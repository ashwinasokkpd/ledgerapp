package com.ledger.ledgermicroservice.transaction.model;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionEntryRecord(UUID accountId, Direction direction, BigDecimal amount) {
}