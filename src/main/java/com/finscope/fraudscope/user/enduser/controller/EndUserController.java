package com.finscope.fraudscope.user.enduser.controller;

import static com.finscope.fraudscope.common.util.SecurityContextUtil.getCurrentUsername;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.StandartResponse;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUserIU;
import com.finscope.fraudscope.user.enduser.service.EndUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "User Profile", description = "Endpoints for completing user profile information")
@RestController
@RequestMapping("/api/end-user/profile")
@RequiredArgsConstructor
@Slf4j
public class EndUserController extends BaseResponseController {

	private final EndUserService endUserService;

	@Operation(summary = "Complete user profile", description = "Allows the authenticated user to complete their profile by providing personal information such as name, surname, phone number, address, country, and date of birth.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User profile completed successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "409", description = "User profile already exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('E_USER_PROFILE_COMPLETE')")
	@PostMapping("/complete")
	public ResponseEntity<StandartResponse<DtoEndUser>> completeUserProfile(
			@Valid @RequestBody DtoEndUserIU dtoUserIU) {

		return created(endUserService.completeEndUserProfile(dtoUserIU, getCurrentUsername()));
	}

	
	@Operation(summary = "Get user profile details", description = "Allows the authenticated use to fetch.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "User profil details fetch successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('E_USER_PROFILE_DELETE')")
	@GetMapping("/details")
	public ResponseEntity<StandartResponse<DtoEndUser>> getUserProfileDetails() {

		return ok(endUserService.getEndUserProfileDetails(getCurrentUsername()));
	}

	
	@Operation(summary = "Delete authenticated user's account", description = "Deletes the authenticated user's account. The operation performs a soft delete and disables login access.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "User deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('E_USER_DELETE')")
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteEndUser() {
		endUserService.deleteEndUser(getCurrentUsername());
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Update user profile", description = "Allows the authenticated user to update their profile by providing personal information such as name, surname, phone number, address, country, and date of birth.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "User profile updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "409", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('E_USER_PROFILE_UPDATE')")
	@PutMapping("/update")
	public ResponseEntity<StandartResponse<DtoEndUser>> updateUserProfile(
			@Valid @RequestBody DtoEndUserIU dtoUserIU) {

		return ok(endUserService.updateEndUserProfile(dtoUserIU, getCurrentUsername()));
	}

}
