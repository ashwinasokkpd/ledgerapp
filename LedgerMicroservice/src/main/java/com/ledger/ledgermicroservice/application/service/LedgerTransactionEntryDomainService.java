package com.ledger.ledgermicroservice.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

public class LedgerTransactionEntryDomainService implements GetAllLedgerTransactionEntriesForAccountFromTo {
	private final LedgerTransactionEntryPersistancePort transactionEntryPersistancePort;

	public LedgerTransactionEntryDomainService(LedgerTransactionEntryPersistancePort transactionEntryPersistancePort) {
		this.transactionEntryPersistancePort = transactionEntryPersistancePort;
	}

	@Override
	public List<LedgerTransactionEntry> getAllTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception {
		return transactionEntryPersistancePort.getTransactionEntriesForAccountFromTo(accountId, from, to);
	}
}
