package com.finscope.fraudscope.user.admin.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;



@Entity
@Table(name = "admins")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@SQLDelete(sql = "UPDATE admins SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Admin extends User{
	
	@Column(nullable =  false ,name = "title")
	private String title;
	
	@Column(nullable =  false ,name = "registration_number", unique = true)
	private String registrationNumber;
	
}
