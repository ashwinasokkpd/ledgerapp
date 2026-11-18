package com.ledger.ledgermicroservice.adapter.outbound.persistance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.LedgerTransactionPersistanceModel;

public interface LedgerTransactionRepo extends JpaRepository<LedgerTransactionPersistanceModel, UUID> {
}
