package com.ledger.ledgermicroservice.account.ports.inbound;

import java.util.UUID;

import com.ledger.ledgermicroservice.account.model.Account.AccountType;

public interface CreateAccountUseCase {
	void createAccount(AccountDataHolder acc);

	public static record AccountDataHolder(UUID id, String name, AccountType type, Long ownerId) {
	}

}
