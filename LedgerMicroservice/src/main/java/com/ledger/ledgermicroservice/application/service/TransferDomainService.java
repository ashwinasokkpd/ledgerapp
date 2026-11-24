package com.ledger.ledgermicroservice.application.service;

import java.util.List;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;
import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.LedgerDirection;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntryRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;
import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;
import com.ledger.ledgermicroservice.domain.model.TransferRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferDomainService implements CreateTransferUseCase, CreateSelfTransferUseCase {
	private final CreateTransactionUseCase createTransactionUseCase;
	private final FindAccountByOwnerIdUseCase findAccountByOwnerIdUseCase;
	private final UserIdPort userIdPort;

	@Override
	public void createTransferUseCase(TransferRecord rec) throws Exception {

		LedgerTransactionEntryRecord senderRec;
		LedgerTransactionEntryRecord receiverRec;
		senderRec = new LedgerTransactionEntryRecord(rec.sender(), LedgerDirection.CREDIT, rec.amount());
		receiverRec = new LedgerTransactionEntryRecord(rec.receiver(), LedgerDirection.DEBIT, rec.amount());

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(senderRec, receiverRec), "Transfer");
		createTransactionUseCase.createTransactionUseCase(traRec);
	}

	@Override
	public void createSelfTransferUseCase(SelfTransferRecord rec) throws Exception {

		LedgerTransactionEntryRecord senderRec;
		LedgerTransactionEntryRecord receiverRec;
		Long userId = userIdPort.userId();
		Account self = findAccountByOwnerIdUseCase.findAccountByOwnerIdUseCase(userId);

		senderRec = new LedgerTransactionEntryRecord(self.getId(), LedgerDirection.CREDIT, rec.amount());
		receiverRec = new LedgerTransactionEntryRecord(rec.receiver(), LedgerDirection.DEBIT, rec.amount());

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(senderRec, receiverRec), "Transfer");
		createTransactionUseCase.createTransactionUseCase(traRec);
	}
}
