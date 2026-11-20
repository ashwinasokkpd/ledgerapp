package com.ledger.ledgermicroservice.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllAccountsUseCase;
import com.ledger.ledgermicroservice.application.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.domain.model.Account;

public class AccountDomainService
		implements CreateAccountUseCase, GetAllAccountsUseCase, FindAccountByIdUseCase, FindAccountByOwnerIdUseCase {

	private final AccountPersistancePort pers;

	public AccountDomainService(AccountPersistancePort pers) {
		this.pers = pers;
	}

	@Override

	public void createAccount(AccountDataHolder acc) {
		pers.save(Account.createUsing(acc.name(), acc.type(), acc.ownerId(), BigDecimal.ZERO, LocalDateTime.now()));
	}

	@Override
	public List<Account> getAllAccounts() {
		return pers.getAll();
	}

	@Override
	public Account findAccountById(UUID id) {
		return pers.findById(id);
	}

	@Override
	public Account findAccountByOwnerIdUseCase(Long id) {
		// TODO Auto-generated method stub
		return pers.findByOwnerId(id);
	}

}
