package com.finscope.fraudscope.authentication.service.impl;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.dto.AuthReponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.jwt.JWTService;
import com.finscope.fraudscope.authentication.mapper.AuthMapper;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.service.AuthService;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.role.repository.RoleRepository;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
import com.finscope.fraudscope.common.enums.PredefinedRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final BCryptPasswordEncoder passwordEncoder;

	private final AuthUserRepository authUserRepository;
	
	private final RoleRepository roleRepository;
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	private final JWTService jwtService;
	
	private final AuthMapper authMapper;

	@Value("${jwt.refresh.token.expiration-minutes}")
	private long refreshTokenExpiration;

	
   
	
	@Override
	public AuthReponse register(RegisterRequest registerRequest) {
		if(authUserRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
		    throw new IllegalArgumentException("Username already exists");

		}
		if(authUserRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
		    throw new IllegalArgumentException("Email  already exists");

		}
		
		AuthUser authUser = buildAuthUserWithDefaultRole(registerRequest);
	    AuthUser savedUser = authUserRepository.save(authUser);
	    
	    
	     
	    
	    RefreshToken refreshToken = createAndSaveRefreshToken(savedUser , registerRequest.getIpAddress() , registerRequest.getUserAgent());
	    String accessToken = jwtService.generateToken(savedUser);

	    AuthReponse authReponse= authMapper.toResponse(savedUser, refreshToken.getToken(), accessToken);

		return authReponse;
	}
	
	
	private RefreshToken createAndSaveRefreshToken(AuthUser savedAuthUser, String ipAddress, String userAgent) {

	    RefreshToken refreshToken = new RefreshToken();
	    refreshToken.setToken(UUID.randomUUID().toString());
	    refreshToken.setAuthUser(savedAuthUser);
	    refreshToken.setExpiryDate(LocalDateTime.now().plusMinutes(refreshTokenExpiration)); 
	    refreshToken.setIpAddress(ipAddress);
	    refreshToken.setUserAgent(userAgent);

	    refreshTokenRepository.save(refreshToken);
	    
	    return refreshToken;
	}
	
	
	
	private AuthUser buildAuthUserWithDefaultRole(RegisterRequest registerRequest) {
		
		AuthUser authUser = new AuthUser();
		authUser.setUsername(registerRequest.getUsername());
		authUser.setEmail(registerRequest.getEmail());
		authUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		
		// ENUM base Role Asssign
	    Role userRole = roleRepository.findByName(PredefinedRole.USER.name())
	        .orElseThrow(() -> new RuntimeException("Default ROLE is USER not found"));

	    RoleUser roleUser = new RoleUser();
	    roleUser.setRole(userRole);
		roleUser.setAuthUser(authUser);
		
		authUser.setUserRoles(Set.of(roleUser));
		
		
		return authUser;
	}


}


