package com.ledger.ledgermicroservice.adapter.outbound.persistance;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.AccountPersistanceModel;
import com.ledger.ledgermicroservice.adapter.outbound.persistance.repository.AccountRepo;
import com.ledger.ledgermicroservice.application.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.domain.model.Account;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountPersistanceAdapter implements AccountPersistancePort {

	private final AccountRepo repo;
	private final EntityManager entityManager;

	@Override
	public void save(Account account) {
		repo.save(AccountPersistanceModel.fromDomain(account));
	}

	@Override
	public Account findById(UUID id) {
		return repo.findById(id).map(AccountPersistanceModel::toDomain).orElse(null);
	}

	@Override
	public List<Account> getAll() {
		return repo.findAll().stream().map(AccountPersistanceModel::toDomain).toList();
	}

	@Override
	public List<Account> findAndLockAllByUUID(List<UUID> uuids) {
		entityManager.createNativeQuery("SET LOCAL lock_timeout = '5000ms'").executeUpdate();
		List<AccountPersistanceModel> accountsPersList = repo.findAndLockAllByUUID(uuids);
		accountsPersList.forEach(e -> System.out.println(e.getId()));
		return accountsPersList.stream().map(AccountPersistanceModel::toDomain).toList();
	}

	@Override
	public Account findByOwnerId(Long id) {
		return repo.findByOwnerId(id).map(AccountPersistanceModel::toDomain).orElse(null);

	}

}
