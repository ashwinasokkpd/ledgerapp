package com.ledger.ledgermicroservice.transaction.ports.inbound;

import java.util.List;

import com.ledger.ledgermicroservice.transaction.model.Transaction;

public interface GetAllTransactionsUseCase {
	public List<Transaction> getAllTransactionsUseCase() throws Exception;
}
