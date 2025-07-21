package com.finscope.fraudscope.authorization.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.finscope.fraudscope.authorization.bootstrap.service.PermissionSeederService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class PermissionSeeder implements CommandLineRunner {

	private final PermissionSeederService permissionSeederService;
	
	
	@Override
	public void run(String... args) throws Exception {
		permissionSeederService.initSeederAuthUser();
		permissionSeederService.seedPermissons();

	}
	
	
	

}
