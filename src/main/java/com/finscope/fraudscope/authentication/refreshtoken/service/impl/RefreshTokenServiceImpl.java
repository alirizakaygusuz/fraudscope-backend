package com.finscope.fraudscope.authentication.refreshtoken.service.impl;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.jwt.JWTService;
import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenRequest;
import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenResponse;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.refreshtoken.service.RefreshTokenService;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    
    private final JWTService jwtService;

    @Value("${jwt.refresh.token.expiration-minutes}")
    private long refreshTokenExpiration;

    
    private RefreshToken build(AuthUser authUser, String ipAddress, String userAgent) {
        return 
            RefreshToken.builder()
                .authUser(authUser)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusMinutes(refreshTokenExpiration))
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
    }
    
    
    private RefreshToken save(RefreshToken refreshToken) {
          return refreshTokenRepository.save(refreshToken);
    }
    
    @Override
    public RefreshToken createAndSave(AuthUser user, String ip, String agent) {
        RefreshToken token = build(user, ip, agent);
        return save(token);
    }

	@Override
	@Transactional
	public RefreshTokenResponse rotateToken(RefreshTokenRequest request) {
		RefreshToken refreshToken = findByRequestOrThrowError(request);
		
		if(refreshToken.isRevoked() || refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
		    throw new BaseException(new ErrorMessage(ErrorType.REFRESH_TOKEN_INVALID));

		}
		markAsRevoked(refreshToken);

		
		RefreshToken rotatedRefreshToken= build(refreshToken.getAuthUser(), request.getIpAddress(), request.getUserAgent());
		
		refreshTokenRepository.saveAll(List.of(refreshToken, rotatedRefreshToken));
		
		String accessToken = jwtService.generateToken(refreshToken.getAuthUser());

		
		return new RefreshTokenResponse(accessToken, rotatedRefreshToken.getToken());
	}
	
	

	@Override
	@Transactional
	public String logout(RefreshTokenRequest request) {
		RefreshToken refreshToken = findByRequestOrThrowError(request);
		
		markAsRevoked(refreshToken);
		refreshTokenRepository.save(refreshToken);

		return "Succefully Logout";
	}

	private RefreshToken findByRequestOrThrowError(RefreshTokenRequest request) {
		return refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(
				()-> new BaseException(new ErrorMessage(ErrorType.REFRESH_TOKEN_INVALID)));
	}
	
	private void markAsRevoked(RefreshToken refreshToken) {
		refreshToken.setRevoked(true);
		refreshToken.setRevokedAt(LocalDateTime.now());
	}

}
