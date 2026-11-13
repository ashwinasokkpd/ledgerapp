package com.ledger.ledgermicroservice.transaction.adapter.outbound;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.transaction.adapter.outbound.entity.TransactionEntryPersistenceModel;
import com.ledger.ledgermicroservice.transaction.adapter.outbound.repository.TransactionEntryRepo;
import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionEntryPersistancePort;

@Component
public class TransactionEntryPersistanceAdapter implements TransactionEntryPersistancePort {

	private final TransactionEntryRepo repo;

	TransactionEntryPersistanceAdapter(TransactionEntryRepo repo) {
		this.repo = repo;
	}

	@Override
	public List<TransactionEntry> getTransactionEntriesForAccountFromTo(UUID accountId, LocalDateTime from,
			LocalDateTime to) throws Exception {
		List<TransactionEntry> list = new ArrayList<>();
		for (TransactionEntryPersistenceModel ent : repo
				.findByAccountIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(accountId,
						from, to)) {
			TransactionEntry entry = TransactionEntry.builder().setId(ent.getEntryId()).setAccountId(ent.getAccountId())
					.setTransactionId(ent.getTransaction().getTransactionId()).setCreatedAt(ent.getCreatedAt())
					.setAmount(ent.getAmount()).setDirection(ent.getDirection()).build();
			list.add(entry);
		}
		return list;

	}
}
