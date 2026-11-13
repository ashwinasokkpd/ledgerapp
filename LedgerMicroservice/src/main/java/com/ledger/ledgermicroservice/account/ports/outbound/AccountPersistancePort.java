package com.ledger.ledgermicroservice.account.ports.outbound;

import java.util.List;

import com.ledger.ledgermicroservice.account.model.Account;

public interface AccountPersistancePort {
	public void save(Account account);

	public List<Account> getAll();

	public Account findById(Long id);
}
