package com.ledger.api;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ledger.api.users.userroles.UserRole;
import com.ledger.api.users.userroles.UserRolesService;

import org.springframework.boot.SpringApplication;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ledger.api.users.LoginUser;
import com.ledger.api.users.UserService;

@SpringBootApplication
public class LedgerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerAppApplication.class, args);
	}

	@Bean
	CommandLineRunner initialLoginDataInserter(UserRolesService rolesService, UserService userService,
			@Value("${fillwithdummyusers}") boolean fillWithDummyUsers) {
		return args -> {
			if (fillWithDummyUsers) {
				List.of("ROLE_ADMIN", "ROLE_STAFF", "ROLE_CUSTOMER").forEach(r -> {
					if (!rolesService.findByRoleOptional(r).isPresent()) {
						UserRole role = new UserRole(r);
						rolesService.save(role);
					}
				});

				Map.of("ROLE_ADMIN", "admin", "ROLE_STAFF", "staff", "ROLE_CUSTOMER", "customer").entrySet()
						.forEach(e -> {
							UserRole role = rolesService.findByRole(e.getKey());

							try {
								userService.loadUserByUsername(e.getValue());
								// Username exists
							} catch (UsernameNotFoundException ex) {
								LoginUser user = LoginUser.builder(e.getValue(), "pass").enable().addRole(role).build();
								userService.saveUser(user);
							}

						});
			}
		};

	}
}