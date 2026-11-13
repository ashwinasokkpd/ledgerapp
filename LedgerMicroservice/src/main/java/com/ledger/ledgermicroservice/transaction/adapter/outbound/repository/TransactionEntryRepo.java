package com.ledger.ledgermicroservice.transaction.adapter.outbound.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.transaction.adapter.outbound.entity.TransactionEntryPersistenceModel;

public interface TransactionEntryRepo extends JpaRepository<TransactionEntryPersistenceModel, Long> {
	List<TransactionEntryPersistenceModel> findByAccountIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(
			UUID uuid, LocalDateTime from, LocalDateTime to);
}
