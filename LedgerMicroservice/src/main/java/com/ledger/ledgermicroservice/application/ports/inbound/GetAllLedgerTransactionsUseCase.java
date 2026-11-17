package com.ledger.ledgermicroservice.application.ports.inbound;

import java.util.List;

import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;

public interface GetAllLedgerTransactionsUseCase {
	public List<LedgerTransaction> getAllTransactionsUseCase() throws Exception;
}
