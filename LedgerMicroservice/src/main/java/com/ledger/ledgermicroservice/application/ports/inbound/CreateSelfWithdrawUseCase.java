package com.ledger.ledgermicroservice.application.ports.inbound;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateSelfWithdrawUseCase {

	void createSelfWithdrawUseCase(UUID bankAssetAccountId, BigDecimal amount) throws Exception;
}
