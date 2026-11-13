package com.ledger.ledgermicroservice.account.adapter.outbound;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.account.adapter.outbound.entity.AccountPersistanceModel;
import com.ledger.ledgermicroservice.account.adapter.outbound.repository.AccountRepo;
import com.ledger.ledgermicroservice.account.model.Account;
import com.ledger.ledgermicroservice.account.ports.outbound.AccountPersistancePort;

@Component
public class AccountPersistanceAdapter implements AccountPersistancePort {

	private final AccountRepo repo;

	AccountPersistanceAdapter(AccountRepo repo) {
		this.repo = repo;
	}

	@Override
	public void save(Account account) {
		repo.save(AccountPersistanceModel.fromDomain(account));
	}

	@Override
	public Account findById(Long id) {
		return repo.findById(id).map(AccountPersistanceModel::toDomain).orElse(null);
	}

	@Override
	public List<Account> getAll() {
		return repo.findAll().stream().map(AccountPersistanceModel::toDomain).toList();
	}

}
