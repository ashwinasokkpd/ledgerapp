package com.ledger.ledgermicroservice.application.ports.inbound;

import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;

public interface GetLedgerTransactionWithUUID {
	public LedgerTransaction getTransactionWithUUID(UUID transactionID) throws Exception;
}
