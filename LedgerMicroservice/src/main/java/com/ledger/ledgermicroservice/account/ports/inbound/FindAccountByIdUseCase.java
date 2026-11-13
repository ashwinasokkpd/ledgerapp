package com.ledger.ledgermicroservice.account.ports.inbound;

import com.ledger.ledgermicroservice.account.model.Account;

public interface FindAccountByIdUseCase {
	Account findAccountById(Long id);
}
