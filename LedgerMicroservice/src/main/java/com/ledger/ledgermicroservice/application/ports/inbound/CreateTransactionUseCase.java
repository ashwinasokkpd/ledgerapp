package com.ledger.ledgermicroservice.application.ports.inbound;

import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

public interface CreateTransactionUseCase {
	public void createTransactionUseCase(LedgerTransactionRecord rec) throws Exception;

}
