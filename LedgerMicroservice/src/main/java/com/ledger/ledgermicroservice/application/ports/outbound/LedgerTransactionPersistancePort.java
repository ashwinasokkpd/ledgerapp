package com.ledger.ledgermicroservice.application.ports.outbound;

import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;

public interface LedgerTransactionPersistancePort {
	public void save(LedgerTransaction trs);

	public List<LedgerTransaction> getAllTransactions() throws Exception;

	public LedgerTransaction getTransactionWithId(UUID uuid) throws Exception;

}
