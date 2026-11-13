package com.ledger.ledgermicroservice.transaction.adapter.outbound.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.transaction.adapter.outbound.entity.TransactionPersistanceModel;

public interface TransactionRepo extends JpaRepository<TransactionPersistanceModel, Long> {
	public TransactionPersistanceModel findByTransactionId(UUID uuid);
}
