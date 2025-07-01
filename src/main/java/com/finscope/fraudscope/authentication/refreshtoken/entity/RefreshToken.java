package com.finscope.fraudscope.authentication.refreshtoken.entity;

import java.time.LocalDateTime;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.audit.AuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken extends AuditBase {

	@Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;
    
    @Column(nullable = false)
    private boolean revoked = false;

    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;
    
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
