package com.ledger.ledgermicroservice.transaction.ports.inbound;

import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.Transaction;

public interface GetTransactionWithUUID {
	public Transaction getTransactionWithUUID(UUID transactionID) throws Exception;
}
