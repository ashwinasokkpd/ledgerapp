package com.ledger.ledgermicroservice.adapter.outbound.security;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.adapter.inbound.web.filter.UserIdHolder;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;

@Component
public class UserIdAdapter implements UserIdPort {

	@Override
	public Long userId() throws Exception {
		Long userId = UserIdHolder.get();
		if (userId == null) {
			throw new Exception("UserIdHolder does not contain userid");
		}
		return userId;
	}

}
