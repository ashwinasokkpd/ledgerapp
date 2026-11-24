package com.ledger.ledgermicroservice.adapter.inbound.web.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserIdExctractorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String userIdHeaderString = request.getHeader("X-UserID");

			if (userIdHeaderString == null) {
				throw new ServletException("X-UserID not set");
			}
			Long userId = Long.parseLong(userIdHeaderString);
			UserIdHolder.set(userId);
			filterChain.doFilter(request, response);
		} finally {
			UserIdHolder.clear();
		}

	}

}
