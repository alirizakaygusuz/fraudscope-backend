package com.finscope.fraudscope.authentication.refreshtoken.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.refreshtoken.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.token.expiration-minutes}")
    private long refreshTokenExpiration;

    @Override
    public RefreshToken build(AuthUser authUser, String ipAddress, String userAgent) {
        return 
            RefreshToken.builder()
                .authUser(authUser)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusMinutes(refreshTokenExpiration))
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
    }
    
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
          return refreshTokenRepository.save(refreshToken);
    }
    
    @Override
    public RefreshToken createAndSave(AuthUser user, String ip, String agent) {
        RefreshToken token = build(user, ip, agent);
        
        return save(token);
    }
}
