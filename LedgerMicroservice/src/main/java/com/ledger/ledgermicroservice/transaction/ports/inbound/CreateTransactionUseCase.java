package com.ledger.ledgermicroservice.transaction.ports.inbound;

import com.ledger.ledgermicroservice.transaction.model.TransactionRecord;

public interface CreateTransactionUseCase {
	public void createTransactionUseCase(TransactionRecord rec) throws Exception;
}
