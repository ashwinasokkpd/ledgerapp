package com.ledger.ledgermicroservice.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ledger.ledgermicroservice.adapter.inbound.web.filter.UserIdExctractorFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class LedgerMicroserviceConfig {

	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity http, UserIdExctractorFilter userIdExctractorFilter) {
		//@formatter:off
		return http.authorizeHttpRequests(auth -> 
		                auth.requestMatchers(org.springframework.http.HttpMethod.OPTIONS,"/**").permitAll()
		                    .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()     
		                    .anyRequest().permitAll())
                   .csrf(AbstractHttpConfigurer::disable)
                   .sessionManagement(session -> 
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .addFilterBefore(userIdExctractorFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();
		//@formatter:on
	}
}