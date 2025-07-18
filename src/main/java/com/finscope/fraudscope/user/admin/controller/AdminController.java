package com.finscope.fraudscope.user.admin.controller;

import static com.finscope.fraudscope.common.util.SecurityContextUtil.getCurrentUsername;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.StandartResponse;
import com.finscope.fraudscope.user.admin.dto.DtoAdmin;
import com.finscope.fraudscope.user.admin.dto.DtoAdminIU;
import com.finscope.fraudscope.user.admin.service.AdminService;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Admin User Management", description = "Endpoints for managing user profile as admin")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController extends BaseResponseController{

	private final AdminService adminService;
	
	
	
	@Operation(summary = "Complete admin profile", description = "Allows admin to complete his profile by providing personal information such as name, surname, phone number, title, registration number.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Admin profile completed successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "409", description = "Admin profile already exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_ADMIN_PROFILE_COMPLETE')")
	@PostMapping("/complete")
	public ResponseEntity<StandartResponse<DtoAdmin>> completeUserProfile(
			@Valid @RequestBody DtoAdminIU dtoAdminIU) {

		return created(adminService.completeAdminProfile(dtoAdminIU, getCurrentUsername()));
	}
	
	@Operation(summary = "Get admin profile details", description = "Allows the authenticated use to fetch.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Admin profil details fetch successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_ADMIN_PROFILE_VIEW')")
	@GetMapping("/details")
	public ResponseEntity<StandartResponse<DtoAdmin>> getAdminProfileDetails() {

		return ok(adminService.getAdminProfileDetails(getCurrentUsername()));
	}

	@Operation(summary = "Update admin profile", description = "Allows to admin to update his profile by providing personal information such as name, surname, phone number, title, registration number.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Admin profile updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "409", description = "Admin profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_ADMIN_PROFILE_UPDATE')")
	@PutMapping("/update")
	public ResponseEntity<StandartResponse<DtoAdmin>> updateUserProfile(
			@Valid @RequestBody DtoAdminIU dtoAdminIU) {

		return ok(adminService.updateAdminProfile(dtoAdminIU, getCurrentUsername()));
	}
	
	

	@Operation(summary = "Get All Users profile details", description = "Allows admin to fetch all users profiles.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "All Users profiles fetch successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_USER_PROFILES_VIEW')")
	@GetMapping("/users/all")
	public ResponseEntity<StandartResponse<List<DtoEndUser>>> getAllUserProfilesDetails() {
		
		return ok(adminService.getAllUserProfilesDetails());
	}

	

	@Operation(summary = "Get selected User profile details", description = "Allows admin to fetch a user by username or email.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Selected user profile fetche successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_USER_PROFILE_VIEW')")
	@GetMapping("/users/details")
	public ResponseEntity<StandartResponse<DtoEndUser>> getSelectedUserProfilesDetails(@RequestParam String usernameOrEmail) {
		
		return ok(adminService.getSelectedUserProfilesDetails(usernameOrEmail));
	}
	
	
	@Operation(summary = "Delete selected user's account", description = "Deletes selected user's account. The operation performs a soft delete and disables login access.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "User deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "404", description = "User profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_USER_PROFILE_DELETE')")
	@DeleteMapping("/users/delete")
	public ResponseEntity<Void> deleteEndUser(@RequestParam  String usernameOrEmail) {
		adminService.deleteSelectedEndUser(usernameOrEmail,getCurrentUsername());
		return ResponseEntity.noContent().build();
	}

		
}
