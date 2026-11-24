package com.ledger.ledgermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class LedgerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerMicroserviceApplication.class, args);
	}

}
