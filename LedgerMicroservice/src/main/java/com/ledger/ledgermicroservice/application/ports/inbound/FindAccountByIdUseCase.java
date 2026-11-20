package com.ledger.ledgermicroservice.application.ports.inbound;

import java.util.UUID;

import com.ledger.ledgermicroservice.domain.model.Account;

public interface FindAccountByIdUseCase {
	Account findAccountById(UUID id);

}
