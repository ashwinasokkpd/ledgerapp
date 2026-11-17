package com.ledger.ledgermicroservice.application.ports.inbound;

import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

public interface CreateLedgerTransactionUseCase {
	public void createLedgerTransactionUseCase(LedgerTransactionRecord rec) throws Exception;
}
