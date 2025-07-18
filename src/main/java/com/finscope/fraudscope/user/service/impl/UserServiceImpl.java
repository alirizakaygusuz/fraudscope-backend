package com.finscope.fraudscope.user.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
import com.finscope.fraudscope.user.admin.entity.Admin;
import com.finscope.fraudscope.user.admin.repository.AdminRepository;
import com.finscope.fraudscope.user.enduser.entity.EndUser;
import com.finscope.fraudscope.user.enduser.repository.EndUserRepository;
import com.finscope.fraudscope.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final EndUserRepository endUserRepository;
	
	private final AdminRepository adminRepository;
	
	private final AuthUserRepository authUserRepository;

	@Override
	public void throwIfAccountExists(Long  authUserId) {
		Optional<EndUser> existsUser = endUserRepository.findByAuthUserId(authUserId);
		Optional<Admin> existsAdmin = adminRepository.findByAuthUserId(authUserId);

		if (existsUser.isPresent()) {
			throw new BaseException(new ErrorMessage(ErrorType.USER_ALREADY_EXISTS));
		}
		
		if (existsAdmin.isPresent()) {
			throw new BaseException(new ErrorMessage(ErrorType.ADMIN_ALREADY_EXISTS));
		}
		
		
	}
	
	@Override
	public EndUser fetchEndUserOrThrow(Long authUserId) {
		return endUserRepository.findByAuthUserId(authUserId)
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.USER_NOT_FOUND)));
	}


	@Override
	public AuthUser fetchAuthUserOrThrow(String usernameOrEmail) {
		return authUserRepository.findByUsernameOrEmail(usernameOrEmail)
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.AUTH_USER_NOT_FOUND)));
	}
}
