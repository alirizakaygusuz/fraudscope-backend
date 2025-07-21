package com.finscope.fraudscope.user.enduser.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUserIU;
import com.finscope.fraudscope.user.enduser.entity.EndUser;
import com.finscope.fraudscope.user.enduser.mapper.EndUserMapper;
import com.finscope.fraudscope.user.enduser.repository.EndUserRepository;
import com.finscope.fraudscope.user.enduser.service.EndUserService;
import com.finscope.fraudscope.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EndUserServiceImpl implements EndUserService {

	private final EndUserRepository endUserRepository;

	private final AuthUserRepository authUserRepository;

	private final EndUserMapper endUserMapper;

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserService userService;

	@Override
	@Transactional
	public DtoEndUser completeEndUserProfile(DtoEndUserIU dtoUserIU, String currentUsername) {

		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);
		userService.throwIfAccountExists(authUser.getId());

		EndUser user = endUserMapper.toEntity(dtoUserIU);
		user.setAuthUser(authUser);

		EndUser savedUser = endUserRepository.save(user);

		return endUserMapper.toDto(savedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public DtoEndUser getEndUserProfileDetails(String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);

		EndUser user = userService.fetchEndUserOrThrow(authUser.getId());

		return endUserMapper.toDto(user);
	}

	@Override
	@Transactional
	public void deleteEndUser(String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);
		Optional<EndUser> endUser = endUserRepository.findByAuthUserId(authUser.getId());

		if (endUser.isPresent()) {
			endUserRepository.delete(endUser.get());

		}
		refreshTokenRepository.revokeValidRefreshTokenForAuthUser(authUser.getId());

		authUserRepository.delete(authUser);

	}

	@Override
	public DtoEndUser updateEndUserProfile(DtoEndUserIU dtoUserIU, String currentUsername) {
		AuthUser authUser = userService.fetchAuthUserOrThrow(currentUsername);
		EndUser existingEndUser = userService.fetchEndUserOrThrow(authUser.getId());

		endUserMapper.updateFromDtoIU(dtoUserIU, existingEndUser);

		EndUser updatedUser = endUserRepository.save(existingEndUser);

		return endUserMapper.toDto(updatedUser);

	}

}
