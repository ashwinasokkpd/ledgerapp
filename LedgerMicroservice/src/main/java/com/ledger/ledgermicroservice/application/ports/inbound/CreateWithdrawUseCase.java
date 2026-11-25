package com.ledger.ledgermicroservice.application.ports.inbound;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateWithdrawUseCase {

	void createWithdrawUseCase(UUID accountId, UUID bankAssetAccountId, BigDecimal amount) throws Exception;
}
