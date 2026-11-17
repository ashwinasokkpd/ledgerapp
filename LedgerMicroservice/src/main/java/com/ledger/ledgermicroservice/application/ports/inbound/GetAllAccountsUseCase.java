package com.ledger.ledgermicroservice.application.ports.inbound;

import java.util.List;

import com.ledger.ledgermicroservice.domain.model.Account;

public interface GetAllAccountsUseCase {
	List<Account> getAllAccounts();
}
