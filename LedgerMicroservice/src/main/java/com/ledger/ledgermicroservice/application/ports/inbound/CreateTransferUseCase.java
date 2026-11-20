package com.ledger.ledgermicroservice.application.ports.inbound;

import com.ledger.ledgermicroservice.domain.model.TransferRecord;

public interface CreateTransferUseCase {
	public void createTransferUseCase(TransferRecord rec) throws Exception;
}
