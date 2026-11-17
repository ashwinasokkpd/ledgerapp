package com.ledger.ledgermicroservice.adapter.outbound.persistance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.LedgerTransactionEntryPersistenceModel;
import com.ledger.ledgermicroservice.adapter.outbound.persistance.repository.LedgerTransactionEntryRepo;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.domain.model.AccountDeltaRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

@Component
public class LedgerTransactionEntryPersistanceAdapter implements LedgerTransactionEntryPersistancePort {

	private final LedgerTransactionEntryRepo repo;

	LedgerTransactionEntryPersistanceAdapter(LedgerTransactionEntryRepo repo) {
		this.repo = repo;
	}

	@Override
	public List<LedgerTransactionEntry> getTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception {
		List<LedgerTransactionEntry> list = new ArrayList<>();
		for (LedgerTransactionEntryPersistenceModel ent : repo
				.findByAccountIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(accountId,
						from, to)) {
			LedgerTransactionEntry entry = LedgerTransactionEntry.builder().setId(ent.getId())
					.setAccountId(ent.getAccountId()).setTransactionId(ent.getTransaction().getTransactionId())
					.setCreatedAt(ent.getCreatedAt()).setAmount(ent.getAmount()).setDirection(ent.getDirection())
					.build();
			list.add(entry);
		}
		return list;

	}

	public List<AccountDeltaRecord> getAccountDeltaFor(List<LocalDateTime> createdAts, List<UUID> accountIds) {
		return repo.getAccountDeltaFor(createdAts.toArray(new LocalDateTime[0]), accountIds.toArray(new UUID[0]));
	}

}
