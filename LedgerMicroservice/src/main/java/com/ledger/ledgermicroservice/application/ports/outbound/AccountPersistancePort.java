package com.ledger.ledgermicroservice.application.ports.outbound;

import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.Account;

public interface AccountPersistancePort {
	public void save(Account account);

	public List<Account> getAll();

	public Account findById(UUID id);

	public List<Account> findAndLockAllByUUID(List<UUID> uuids);

}
