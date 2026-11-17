package com.ledger.ledgermicroservice.application.ports.inbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

public interface GetAllLedgerTransactionEntriesForAccountFromTo {
	List<LedgerTransactionEntry> getAllTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception;
}
