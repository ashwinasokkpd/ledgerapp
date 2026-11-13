package com.ledger.ledgermicroservice.transaction.ports.inbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;

public interface GetAllTransactionEntriesForAccountFromTo {
	List<TransactionEntry> getAllTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception;
}
