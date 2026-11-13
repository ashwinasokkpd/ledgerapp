package com.ledger.ledgermicroservice.transaction.application.service;

import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.Transaction;
import com.ledger.ledgermicroservice.transaction.model.Transaction.TransactionBuilder;
import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;
import com.ledger.ledgermicroservice.transaction.model.TransactionEntryRecord;
import com.ledger.ledgermicroservice.transaction.model.TransactionRecord;
import com.ledger.ledgermicroservice.transaction.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetAllTransactionsUseCase;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetTransactionWithUUID;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionPersistancePort;

public class TransactionDomainService
		implements CreateTransactionUseCase, GetAllTransactionsUseCase, GetTransactionWithUUID {

	private final TransactionPersistancePort transactionPersistancePort;

	public TransactionDomainService(TransactionPersistancePort transactionPersistancePort) {
		this.transactionPersistancePort = transactionPersistancePort;
	}

	@Override
	public void createTransactionUseCase(TransactionRecord rec) throws Exception {
		TransactionBuilder builder = Transaction.builder().genenrateNewId().createdNow().setDesc(rec.desc());

		for (TransactionEntryRecord entr : rec.entries()) {
			TransactionEntry lEntr = TransactionEntry.builder().generateNewId().now().setAccountId(entr.accountId())
					.setDirection(entr.direction()).setAmount(entr.amount()).build();
			builder.addEntry(lEntr);
		}
		transactionPersistancePort.save(builder.build());

	}

	@Override
	public List<Transaction> getAllTransactionsUseCase() throws Exception {
		return transactionPersistancePort.getAllTransactions();
	}

	@Override
	public Transaction getTransactionWithUUID(UUID transactionID) throws Exception {
		return transactionPersistancePort.getTransactionWithId(transactionID);
	}

}
