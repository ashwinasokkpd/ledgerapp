package com.ledger.ledgermicroservice.account.ports.inbound;

import java.util.List;

import com.ledger.ledgermicroservice.account.model.Account;

public interface GetAllAccountsUseCase {
	List<Account> getAllAccounts();
}
