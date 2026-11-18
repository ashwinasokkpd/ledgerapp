package com.ledger.ledgermicroservice.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateLedgerTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionsUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetLedgerTransactionWithUUID;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionPersistancePort;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction.TransactionBuilder;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntryRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

public class LedgerTransactionDomainService
		implements CreateLedgerTransactionUseCase, GetAllLedgerTransactionsUseCase, GetLedgerTransactionWithUUID {

	private final LedgerTransactionPersistancePort transactionPersistancePort;

	public LedgerTransactionDomainService(LedgerTransactionPersistancePort transactionPersistancePort) {
		this.transactionPersistancePort = transactionPersistancePort;
	}

	@Override
	public void createLedgerTransactionUseCase(LedgerTransactionRecord rec) throws Exception {

		LocalDateTime now = LocalDateTime.now();
		TransactionBuilder builder = LedgerTransaction.builder().setcreatedAt(now).setDesc(rec.desc());

		for (LedgerTransactionEntryRecord entr : rec.entries()) {
			LedgerTransactionEntry lEntr = LedgerTransactionEntry.builder().setCreatedAt(now)
					.setAccountId(entr.accountId()).setDirection(entr.direction()).setAmount(entr.amount()).build();
			builder.addEntry(lEntr);
		}
		transactionPersistancePort.save(builder.build());

	}

	@Override
	public List<LedgerTransaction> getAllTransactionsUseCase() throws Exception {
		return transactionPersistancePort.getAllTransactions();
	}

	@Override
	public LedgerTransaction getTransactionWithUUID(UUID transactionID) throws Exception {
		return transactionPersistancePort.getTransactionWithId(transactionID);
	}

}
