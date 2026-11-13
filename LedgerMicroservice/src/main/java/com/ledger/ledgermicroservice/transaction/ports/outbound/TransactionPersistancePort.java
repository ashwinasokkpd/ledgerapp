package com.ledger.ledgermicroservice.transaction.ports.outbound;

import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.Transaction;

public interface TransactionPersistancePort {
	public void save(Transaction trs);

	public List<Transaction> getAllTransactions() throws Exception;

	public Transaction getTransactionWithId(UUID uuid) throws Exception;

}
