package com.ledger.ledgermicroservice.account.application.service;

import java.util.List;

import com.ledger.ledgermicroservice.account.model.Account;
import com.ledger.ledgermicroservice.account.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.account.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.account.ports.inbound.GetAllAccountsUseCase;
import com.ledger.ledgermicroservice.account.ports.outbound.AccountPersistancePort;

public class AccountDomainService implements CreateAccountUseCase, GetAllAccountsUseCase, FindAccountByIdUseCase {

	private final AccountPersistancePort pers;

	public AccountDomainService(AccountPersistancePort pers) {
		this.pers = pers;
	}

	@Override
	public void createAccount(AccountDataHolder acc) {
		pers.save(Account.createUsing(acc.name(), acc.type(), acc.ownerId()));
	}

	@Override
	public List<Account> getAllAccounts() {
		return pers.getAll();
	}

	@Override
	public Account findAccountById(Long id) {
		return pers.findById(id);
	}

}
