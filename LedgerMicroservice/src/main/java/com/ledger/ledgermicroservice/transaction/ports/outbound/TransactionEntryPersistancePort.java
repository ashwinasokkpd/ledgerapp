package com.ledger.ledgermicroservice.transaction.ports.outbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;

public interface TransactionEntryPersistancePort {
	public List<TransactionEntry> getTransactionEntriesForAccountFromTo(UUID uuid, LocalDateTime from, LocalDateTime to)
			throws Exception;
}
