package com.ledger.ledgermicroservice.application.ports.inbound;

import com.ledger.ledgermicroservice.domain.model.Account;

public interface FindAccountByOwnerIdUseCase {
	Account findAccountByOwnerIdUseCase(Long id);

}
