package com.finscope.fraudscope.authorization.bootstrap.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authorization.permission.entity.Permission;
import com.finscope.fraudscope.authorization.permission.enums.PredefinedPermisson;
import com.finscope.fraudscope.authorization.permission.repository.PermissionRepository;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.role.enums.PredefinedRole;
import com.finscope.fraudscope.authorization.role.repository.RoleRepository;
import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;
import com.finscope.fraudscope.authorization.rolepermission.repository.RolePermissionRepository;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
import com.finscope.fraudscope.authorization.roleuser.repository.RoleUserRepository;
import com.finscope.fraudscope.common.enums.ApprovalStatus;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionSeederService {
private final PermissionRepository permissionRepository;
	
	private final RolePermissionRepository rolePermissionRepository;
	
	private final RoleRepository roleRepository;
	
	private final AuthUserRepository authUserRepository;
	
	private final RoleUserRepository roleUserRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private AuthUser systemAuthUser;
	
	@Value("${permission.seeder.system-auth-user.name}")
	private String systemAuthUserName;
	
	@Value("${permission.seeder.system-auth-user.email}")
	private String systemAuthUserEmail;

	@Value("${permission.seeder.system-auth-user.password}")
	private String systemAuthUserPassword;
	
	
	@Transactional
	public void seedPermissons() {
		for (PredefinedPermisson predefined : PredefinedPermisson.values()) {
			
			String[] descriptionArray = predefined.getCode().substring(2).toLowerCase().split("_");
			
			String description = "Allows " +	String.join(" ", descriptionArray);
			
			
			Permission permission = permissionRepository.findByName(predefined.getCode())
					.orElseGet(() -> {
						Permission newPermission = Permission.builder()
			                    .name(predefined.getCode())
			                    .description(description)
			                    .build();
						 return permissionRepository.save(newPermission);
					});

	        if(predefined.name().startsWith("E_")) {
	        	assignPermissionToUser(permission);
	        	assignPermmisonToSuperAdmin(permission);
			}
			
			if(predefined.name().startsWith("S_")) {
				assignPermmisonToSuperAdmin(permission);
			}
		
	    }
	}
	
	
	private void assignPermissionToUser(Permission permission) {
		Role role = fetchRoleIfExistsOrThrow(PredefinedRole.USER);
		
		createRolePermissionAndSave(role, permission);	
	}
		
		
	
	private void assignPermmisonToSuperAdmin(Permission permission) {
		Role role = fetchRoleIfExistsOrThrow(PredefinedRole.SUPER_ADMIN);
		
		createRolePermissionAndSave(role, permission);
	}
	
	
	private RolePermission createRolePermissionAndSave(Role role,Permission permission) {
		Optional<RolePermission> existsRolePermission = rolePermissionRepository.findByRoleAndPermission(role , permission);
		
		if(existsRolePermission.isPresent()) {
			return existsRolePermission.get();
		}
		
		RolePermission rolePermission =  RolePermission.builder()
				.role(role)
				.permission(permission)
				.grantedBy(systemAuthUser)
				.grantedAt(LocalDateTime.now())
				.isActive(true)
				.approvalStatus(ApprovalStatus.APPROVED)
				.approvedBy(systemAuthUser)
				.approvedAt(LocalDateTime.now())
				.note("Created By System")
				.build();
		
		
		return rolePermissionRepository.save(rolePermission);
	}
	
	
	@Transactional
	public void initSeederAuthUser() {
		Role role = fetchRoleIfExistsOrThrow(PredefinedRole.SUPER_ADMIN);
		
		if(!checkIfSeederUserAlreadyExists()) {
			systemAuthUser = createSystemAuthUser();

			RoleUser roleUser = assignRoleToAuthUser(role);		
			systemAuthUser.setUserRoles(new HashSet<>(Set.of(roleUser)));
			authUserRepository.save(systemAuthUser);
			
			
			log.info("Seeder auth user and role (RoleUser) were created successfully by system.");
		}
		
		
	}
	
	private Role fetchRoleIfExistsOrThrow(PredefinedRole predefinedRole) {
		return roleRepository.findByName(predefinedRole.name()).orElseThrow(()-> new BaseException(new ErrorMessage(ErrorType.ROLE_NOT_FOUND)));
	}
	
	
	private AuthUser createSystemAuthUser() {
		AuthUser user = AuthUser.builder()
				.username(systemAuthUserName)
				.email(systemAuthUserEmail)
				.password(bCryptPasswordEncoder.encode(systemAuthUserPassword))
				.isEnabled(true)
				.twoFactorEnabled(true)
				.build();
		
		return authUserRepository.save(user);
	}
	
	private RoleUser assignRoleToAuthUser(Role role) {
		RoleUser roleUser = RoleUser.builder()
				.role(role)
				.authUser(systemAuthUser)
				.build();
		
		return roleUserRepository.save(roleUser);
	}
	private boolean checkIfSeederUserAlreadyExists() {
		Optional<AuthUser> existing = authUserRepository.findByEmail(systemAuthUserEmail);
		if (existing.isPresent()) {
			systemAuthUser = existing.get();
			log.info("Seeder auth user already exists");
			return true;
		}
		log.info("Seeder auth user does not exists yet");

		return false;
	}
}
