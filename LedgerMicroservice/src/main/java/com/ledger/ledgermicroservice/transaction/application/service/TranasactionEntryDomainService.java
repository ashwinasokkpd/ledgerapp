package com.ledger.ledgermicroservice.transaction.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetAllTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionEntryPersistancePort;

public class TranasactionEntryDomainService implements GetAllTransactionEntriesForAccountFromTo {
	private final TransactionEntryPersistancePort transactionEntryPersistancePort;

	public TranasactionEntryDomainService(TransactionEntryPersistancePort transactionEntryPersistancePort) {
		this.transactionEntryPersistancePort = transactionEntryPersistancePort;
	}

	@Override
	public List<TransactionEntry> getAllTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception {
		return transactionEntryPersistancePort.getTransactionEntriesForAccountFromTo(accountId, from, to);
	}
}
