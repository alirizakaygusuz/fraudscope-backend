package com.finscope.fraudscope.user.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	    name = "users",
	    indexes = {
	        @Index(name = "idx_users_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends SoftDeletableAuditBase {
	
	@OneToOne
	@JoinColumn(name = "auth_user_id", nullable = false,unique = true)
	private AuthUser authUser;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "phone_number" , length = 20)
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
	
	@Column(name = "is_verified", nullable = false)
	private boolean isVerified = false;

	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Account> accounts;


}