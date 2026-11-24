package com.ledger.ledgermicroservice.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateLedgerTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.application.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;
import com.ledger.ledgermicroservice.application.service.AccountDomainService;
import com.ledger.ledgermicroservice.application.service.LedgerTranasactionEntryDomainService;
import com.ledger.ledgermicroservice.application.service.LedgerTransactionDomainService;
import com.ledger.ledgermicroservice.application.service.TransferDomainService;

@Configuration
public class Config {
	@Bean
	CreateAccountUseCase getCreateAccountUseCase(AccountPersistancePort port) {
		return new AccountDomainService(port);
	}

	@Bean
	CreateLedgerTransactionUseCase getCreateTransactionUseCase(LedgerTransactionPersistancePort port) {
		return new LedgerTransactionDomainService(port);
	}

	@Bean
	GetAllLedgerTransactionEntriesForAccountFromTo getGetAllTransactionEntriesForAccountFromTo(
			LedgerTransactionEntryPersistancePort port) {
		return new LedgerTranasactionEntryDomainService(port);
	}

	@Bean
	CreateTransferUseCase getCreateTransferUseCase(CreateTransactionUseCase createTransactionUseCase,
			FindAccountByOwnerIdUseCase findAccountByOwnerIdUseCase, UserIdPort userIdPort) {

		return new TransferDomainService(createTransactionUseCase, findAccountByOwnerIdUseCase, userIdPort);
	}

}
