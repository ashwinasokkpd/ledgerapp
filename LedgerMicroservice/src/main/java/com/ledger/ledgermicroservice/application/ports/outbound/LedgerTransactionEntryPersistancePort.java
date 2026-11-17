package com.ledger.ledgermicroservice.application.ports.outbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.AccountDeltaRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

public interface LedgerTransactionEntryPersistancePort {
	public List<LedgerTransactionEntry> getTransactionEntriesForAccountFromTo(UUID uuid, LocalDateTime from,
			LocalDateTime to) throws Exception;

	List<AccountDeltaRecord> getAccountDeltaFor(List<LocalDateTime> createdAts, List<UUID> accountIds);
}
