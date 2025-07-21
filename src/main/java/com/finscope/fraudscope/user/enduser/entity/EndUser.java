package com.finscope.fraudscope.user.enduser.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	    name = "endusers",
	    indexes = {
	        @Index(name = "idx_endusers_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE endusers SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class EndUser extends User {
	
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Account> accounts;


}