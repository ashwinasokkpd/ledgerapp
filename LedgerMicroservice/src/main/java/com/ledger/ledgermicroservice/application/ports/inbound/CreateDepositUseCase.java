package com.ledger.ledgermicroservice.application.ports.inbound;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateDepositUseCase {

	void createDepositUseCase(UUID accountId, UUID bankAssetAccountId, BigDecimal amount) throws Exception;
}
