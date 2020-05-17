package com.edu.lnu.mongoDbPpoject;

import com.edu.lnu.mongoDbPpoject.model.Role;
import com.edu.lnu.mongoDbPpoject.repository.mongoRepository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MongoDbProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDbProjectApplication.class, args);
	}
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {

		return args -> {

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}
		};
	}
}

