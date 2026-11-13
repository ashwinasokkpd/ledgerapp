package com.ledger.ledgermicroservice.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ledger.ledgermicroservice.account.application.service.AccountDomainService;
import com.ledger.ledgermicroservice.account.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.account.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.transaction.application.service.TranasactionEntryDomainService;
import com.ledger.ledgermicroservice.transaction.application.service.TransactionDomainService;
import com.ledger.ledgermicroservice.transaction.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetAllTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionPersistancePort;

@Configuration
public class Config {
	@Bean
	CreateAccountUseCase getCreateAccountUseCase(AccountPersistancePort port) {
		return new AccountDomainService(port);
	}

	@Bean
	CreateTransactionUseCase getCreateTransactionUseCase(TransactionPersistancePort port) {
		return new TransactionDomainService(port);
	}

	@Bean
	GetAllTransactionEntriesForAccountFromTo getGetAllTransactionEntriesForAccountFromTo(
			TransactionEntryPersistancePort port) {
		return new TranasactionEntryDomainService(port);
	}

}
