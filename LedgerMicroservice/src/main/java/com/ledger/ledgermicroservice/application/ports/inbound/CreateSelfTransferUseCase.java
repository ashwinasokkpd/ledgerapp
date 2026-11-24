package com.ledger.ledgermicroservice.application.ports.inbound;

import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;

public interface CreateSelfTransferUseCase {
	public void createSelfTransferUseCase(SelfTransferRecord rec) throws Exception;
}
