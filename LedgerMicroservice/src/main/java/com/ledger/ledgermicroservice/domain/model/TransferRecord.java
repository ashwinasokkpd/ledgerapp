package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRecord(UUID sender, UUID receiver, BigDecimal amount) {
}
