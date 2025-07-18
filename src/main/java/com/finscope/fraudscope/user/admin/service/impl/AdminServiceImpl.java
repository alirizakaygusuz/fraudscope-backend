package com.finscope.fraudscope.user.admin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
import com.finscope.fraudscope.user.admin.dto.DtoAdmin;
import com.finscope.fraudscope.user.admin.dto.DtoAdminIU;
import com.finscope.fraudscope.user.admin.entity.Admin;
import com.finscope.fraudscope.user.admin.mapper.AdminMapper;
import com.finscope.fraudscope.user.admin.repository.AdminRepository;
import com.finscope.fraudscope.user.admin.service.AdminService;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;
import com.finscope.fraudscope.user.enduser.entity.EndUser;
import com.finscope.fraudscope.user.enduser.mapper.EndUserMapper;
import com.finscope.fraudscope.user.enduser.repository.EndUserRepository;
import com.finscope.fraudscope.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;

	private final EndUserRepository endUserRepository;

	private final AuthUserRepository authUserRepository;

	private final RefreshTokenRepository refreshTokenRepository;

	private final EndUserMapper endUserMapper;

	private final AdminMapper adminMapper;

	private final UserService userService;

	@Override
	@Transactional
	public DtoAdmin completeAdminProfile(DtoAdminIU dtoAdminIU, String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);
		userService.throwIfAccountExists(authUser.getId());

		Admin admin = adminMapper.toEntity(dtoAdminIU);
		admin.setAuthUser(authUser);

		Admin savedAdmin = adminRepository.save(admin);

		return adminMapper.toDto(savedAdmin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DtoAdmin getAdminProfileDetails(String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);

		Admin admin = fetchAdminOrThrow(authUser.getId());

		return adminMapper.toDto(admin);
	}

	private Admin fetchAdminOrThrow(Long authUserId) {
		return adminRepository.findByAuthUserId(authUserId)
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.ADMIN_DETAILS_NOT_FOUND)));
	}

	@Override
	@Transactional
	public DtoAdmin updateAdminProfile(DtoAdminIU dtoAdminIU, String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);
		
		Admin existingAdmin = fetchAdminOrThrow(authUser.getId());
		
		adminMapper.updateFromDtoIU(dtoAdminIU, existingAdmin);
		
		Admin updatedAdmin = adminRepository.save(existingAdmin);
		
		return adminMapper.toDto(updatedAdmin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DtoEndUser> getAllUserProfilesDetails() {
		return endUserRepository.findAll().stream().map(endUserMapper::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public DtoEndUser getSelectedUserProfilesDetails(String usernameOrEmail) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(usernameOrEmail);
		EndUser user = userService.fetchEndUserOrThrow(authUser.getId());

		return endUserMapper.toDto(user);
	}

	@Override
	@Transactional
	public void deleteSelectedEndUser(String usernameOrEmail, String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(usernameOrEmail);

		Optional<EndUser> endUser = endUserRepository.findByAuthUserId(authUser.getId());

		if (endUser.isPresent()) {
			endUserRepository.delete(endUser.get());

		}
		refreshTokenRepository.revokeValidRefreshTokenForAuthUser(authUser.getId());

		authUser.setDeletedAt(LocalDateTime.now());
		authUser.setDeletedBy(currentUsername);
		authUserRepository.delete(authUser);
	}

	

}
