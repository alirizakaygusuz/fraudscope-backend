package com.finscope.fraudscope.authentication.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	@Value("${jwt.secret.key}")
	private String SECRET_KEY;

	@Value("${jwt.access.token.expiration-minutes}")
	private Long accessTokenExpiration;

	public String generateToken(AuthUser authUser) {
		Map<String, Object> claims = new HashMap<>();
		
		
		
		List<String> permissions= authUser.getAuthorities()
									.stream()
									.map(GrantedAuthority::getAuthority)
									.collect(Collectors.toList());
		
		List<String> roles = authUser.getUserRoles()
								.stream()
								.map(roleUser-> roleUser.getRole().getName())
								.collect(Collectors.toList());
		
		
		claims.put("permissions", permissions);
		claims.put("roles", roles);
		
		

		return Jwts.builder()
		    .setClaims(claims)
		    .setSubject(authUser.getUsername())
		    .setIssuedAt(new Date())
		    .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 60 * 1000))
		    .signWith(getKey(), SignatureAlgorithm.HS256)
		    .compact();
	}

	public String getUsernameByToken(String token) {
		return extractClaimFromToken(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token) {
	    try {
	        Claims claims = getClaims(token);
	        return new Date().before(claims.getExpiration());
	    } catch (ExpiredJwtException e) {
	        throw new BaseException(new ErrorMessage(ErrorType.EXPIRED_TOKEN, e.getMessage()));
	    } catch (SignatureException e) {
	        throw new BaseException(new ErrorMessage(ErrorType.INVALID_TOKEN_SIGNATURE, e.getMessage()));
	    } catch (MalformedJwtException e) {
	        throw new BaseException(new ErrorMessage(ErrorType.MALFORMED_TOKEN, e.getMessage()));
	    } catch (UnsupportedJwtException e) {
	        throw new BaseException(new ErrorMessage(ErrorType.UNSUPPORTED_TOKEN, e.getMessage()));
	    } catch (Exception e) {
	        throw new BaseException(new ErrorMessage(ErrorType.GENERAL_TOKEN_EXCEPTION, e.getMessage()));
	    }
	}


	public <T> T extractClaimFromToken(String token, Function<Claims, T> claimsFunction) {
		Claims claims = getClaims(token);

		return claimsFunction.apply(claims);
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder().
				setSigningKey(getKey()).
				build().
				parseClaimsJws(token).
				getBody();

	}

	public Key getKey() {
		byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);

		return Keys.hmacShaKeyFor(bytes);
	}
}
