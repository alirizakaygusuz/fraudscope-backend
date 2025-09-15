package com.finscope.fraudscope.authorization.permission.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authorization.permission.dto.DtoPermission;
import com.finscope.fraudscope.authorization.permission.dto.DtoPermissionIU;
import com.finscope.fraudscope.authorization.permission.service.PermissionService;
import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.StandartResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Permission Management" , description = "Endpoints for managing permissions")
@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController extends BaseResponseController {

	private final PermissionService permissionService;
	

	@Operation(summary = "Get Selected Permission details", description = "Allows admin to fetch selected permission details.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Selected Permission fetch successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_PERMISSION_VIEW')")
	@GetMapping("/details/{name}")
	public ResponseEntity<StandartResponse<DtoPermission>> getPermission(@PathVariable String name){
		return ok(permissionService.getPermission(name));
	}
	
	@Operation(summary = "Get All Permissions details", description = "Allows admin to fetch all permissions.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "All Permissions fetch successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_PERMISSION_VIEW')")
	@GetMapping("/all")
	public ResponseEntity<StandartResponse<List<DtoPermission>>> getAllPermissions(){
		return ok(permissionService.getAllPermissions());
	}
	
	@Operation(summary = "Update Permission's description", description = "Allows to admin to update Permission' description")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Permission updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "409", description = "Admin profile does not exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasAuthority('S_PERMISSION_UPDATE')")
	@PatchMapping("/update")
	public ResponseEntity<StandartResponse<DtoPermission>> updatePermission(@Valid @RequestBody DtoPermissionIU dtoPermissionIU){
		return ok(permissionService.updatePermissionDescription(dtoPermissionIU));
	}
}
