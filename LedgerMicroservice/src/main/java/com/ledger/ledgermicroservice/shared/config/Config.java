package com.ledger.ledgermicroservice.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateLedgerTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.application.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.TransactionalPort;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;
import com.ledger.ledgermicroservice.application.service.AccountDomainService;
import com.ledger.ledgermicroservice.application.service.LedgerTransactionDomainService;
import com.ledger.ledgermicroservice.application.service.LedgerTransactionEntryDomainService;
import com.ledger.ledgermicroservice.application.service.TransactionDomainService;
import com.ledger.ledgermicroservice.application.service.TransferDomainService;

@Configuration
public class Config {
	@Bean
	CreateAccountUseCase getCreateAccountUseCase(AccountPersistancePort port) {
		return new AccountDomainService(port);
	}

	@Bean
	CreateLedgerTransactionUseCase getCreateLedgerTransactionUseCase(LedgerTransactionPersistancePort port) {
		return new LedgerTransactionDomainService(port);
	}

	@Bean
	GetAllLedgerTransactionEntriesForAccountFromTo getGetAllTransactionEntriesForAccountFromTo(
			LedgerTransactionEntryPersistancePort port) {
		return new LedgerTransactionEntryDomainService(port);
	}

	@Bean
	CreateTransactionUseCase getCreateTransactionUseCase(
			LedgerTransactionPersistancePort ledgerTransactionPersistancePort,
			AccountPersistancePort accountPersistancePort,
			LedgerTransactionEntryPersistancePort ledgerTransactionEntryPersistancePort,
			TransactionalPort transactionalPort) {

		return new TransactionDomainService(ledgerTransactionPersistancePort, accountPersistancePort,
				ledgerTransactionEntryPersistancePort, transactionalPort);
	}

	@Bean
	CreateTransferUseCase getCreateTransferUseCase(CreateTransactionUseCase createTransactionUseCase,
			FindAccountByOwnerIdUseCase findAccountByOwnerIdUseCase, UserIdPort userIdPort,
			FindAccountByIdUseCase findAccountByIdUseCase) {

		return new TransferDomainService(createTransactionUseCase, findAccountByOwnerIdUseCase, findAccountByIdUseCase,
				userIdPort);
	}

}
