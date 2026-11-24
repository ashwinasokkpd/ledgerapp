package com.ledger.api.authentication;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CorreleationAndUserIdFilter extends OncePerRequestFilter {
	private static final Set<String> ESSENTIAL_HEADERS = Set.of("content-type", "content-length", "transfer-encoding",
			"accept", "host");

	@Override
	public boolean shouldNotFilter(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("OPTIONS");
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String correlationId = UUID.randomUUID().toString();
		HttpServletRequestWrapper wrapped = new HttpServletRequestWrapper(request) {
			@Override
			public String getHeader(String name) {
				String nameLower = name.toLowerCase();
				return switch (nameLower) {
				case "x-correlation-id" -> correlationId;
				case "x-userid" -> (String) request.getAttribute("USER_ID");
				default -> ESSENTIAL_HEADERS.contains(nameLower) ? super.getHeader(name) : null;
				};

			}

			@Override
			public Enumeration<String> getHeaders(String name) {
				String nameLower = name.toLowerCase();
				Long userId = (Long) request.getAttribute("USER_ID");
				return switch (name.toLowerCase()) {
				case "x-correlation-id" -> Collections.enumeration(List.of(correlationId));
				case "x-userid" -> userId != null ? Collections.enumeration(List.of(userId.toString()))
						: Collections.emptyEnumeration();
				default ->
					ESSENTIAL_HEADERS.contains(nameLower) ? super.getHeaders(name) : Collections.emptyEnumeration();
				};
			}

			@Override
			public Enumeration<String> getHeaderNames() {
				Set<String> headerNames = new LinkedHashSet<>();
				headerNames.add("X-Correlation-Id");
				headerNames.add("X-UserID");
				Enumeration<String> allHeaderNames = super.getHeaderNames();
				if (allHeaderNames != null)
					Collections.list(allHeaderNames).stream().filter(e -> ESSENTIAL_HEADERS.contains(e.toLowerCase()))
							.forEach(headerNames::add);
				return Collections.enumeration(headerNames);
			}
		};

		filterChain.doFilter(wrapped, response);

	}

}
