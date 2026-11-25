package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositWithdrawRecord(UUID depositorId, BigDecimal amount) {

}
