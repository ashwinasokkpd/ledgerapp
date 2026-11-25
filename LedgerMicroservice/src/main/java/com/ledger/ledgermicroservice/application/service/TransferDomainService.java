package com.ledger.ledgermicroservice.application.service;

import java.util.List;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;
import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntryRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;
import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;
import com.ledger.ledgermicroservice.domain.model.TransferRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferDomainService implements CreateTransferUseCase, CreateSelfTransferUseCase {
	private final CreateTransactionUseCase createTransactionUseCase;
	private final FindAccountByOwnerIdUseCase findAccountByOwnerIdUseCase;
	private final FindAccountByIdUseCase findAccountByIdUseCase;
	private final UserIdPort userIdPort;

	@Override
	public void createTransferUseCase(TransferRecord rec) throws Exception {

		LedgerTransactionEntryRecord senderRec;
		LedgerTransactionEntryRecord receiverRec;
		Account sender = findAccountByIdUseCase.findAccountById(rec.sender());
		Account receiver = findAccountByIdUseCase.findAccountById(rec.receiver());
		if (sender.getNormalType() != receiver.getNormalType()) {
			throw new Exception("Normal type mismatch for transfer");
		}

		senderRec = new LedgerTransactionEntryRecord(rec.sender(), sender.getDecreaseDirection(), rec.amount());
		receiverRec = new LedgerTransactionEntryRecord(rec.receiver(), receiver.getIncreaseDirection(), rec.amount());

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(senderRec, receiverRec), "Transfer");
		createTransactionUseCase.createTransactionUseCase(traRec);
	}

	@Override
	public void createSelfTransferUseCase(SelfTransferRecord rec) throws Exception {

		LedgerTransactionEntryRecord senderRec;
		LedgerTransactionEntryRecord receiverRec;
		Long userId = userIdPort.userId();
		Account sender = findAccountByOwnerIdUseCase.findAccountByOwnerIdUseCase(userId);
		Account receiver = findAccountByIdUseCase.findAccountById(rec.receiver());
		if (sender.getNormalType() != receiver.getNormalType()) {
			throw new Exception("Normal type mismatch for transfer");
		}

		senderRec = new LedgerTransactionEntryRecord(sender.getId(), sender.getDecreaseDirection(), rec.amount());
		receiverRec = new LedgerTransactionEntryRecord(rec.receiver(), receiver.getIncreaseDirection(), rec.amount());

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(senderRec, receiverRec), "Self Transfer");
		createTransactionUseCase.createTransactionUseCase(traRec);
	}
}
