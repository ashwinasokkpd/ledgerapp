package com.ledger.ledgermicroservice.application.ports.inbound;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateSelfDepositUseCase {

	void createSelfDepositUseCase(UUID bankAssetAccountId, BigDecimal amount) throws Exception;
}
