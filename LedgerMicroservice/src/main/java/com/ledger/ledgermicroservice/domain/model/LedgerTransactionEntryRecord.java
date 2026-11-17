package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record LedgerTransactionEntryRecord(UUID accountId, LedgerDirection direction, BigDecimal amount) {
}