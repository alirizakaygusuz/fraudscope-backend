package com.finscope.fraudscope.authorization.bootstrap;



import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.role.repository.RoleRepository;
import com.finscope.fraudscope.common.enums.PredefinedRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner{
	
	private final RoleRepository roleRepository;
		
	@Override
	public void run(String... args) throws Exception {
		Arrays.stream(PredefinedRole.values()).forEach(roleType -> {
			if(roleRepository.findByName(roleType.name()).isEmpty()) {
				Role role = new Role();
				role.setName(roleType.name());
				roleRepository.save(role);
				
				 log.info("Seeded role: {}", roleType.name());
			}
		});
		
	}

}

