package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountDeltaRecord(UUID accountUUID, BigDecimal delta, LocalDateTime latestAt) {
}