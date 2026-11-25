package com.ledger.ledgermicroservice.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateDepositUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfDepositUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfWithdrawUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateWithdrawUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.outbound.UserIdPort;
import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.Account.NormalType;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntryRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;
import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;
import com.ledger.ledgermicroservice.domain.model.TransferRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferDomainService implements CreateTransferUseCase, CreateSelfTransferUseCase, CreateDepositUseCase,
		CreateWithdrawUseCase, CreateSelfDepositUseCase, CreateSelfWithdrawUseCase {
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

	@Override
	public void createDepositUseCase(UUID accountId, UUID bankAssetAccountId, BigDecimal amount) throws Exception {
		LedgerTransactionEntryRecord assetRec;
		LedgerTransactionEntryRecord liabilityRec;
		Account bankAssetAccount = findAccountByIdUseCase.findAccountById(bankAssetAccountId);
		Account liabilityAccount = findAccountByIdUseCase.findAccountById(accountId);
		if (liabilityAccount.getNormalType() != NormalType.NORMAL_CREDIT_ACCOUNT) {
			throw new Exception("Not a liabiliy account");
		}

		assetRec = new LedgerTransactionEntryRecord(bankAssetAccountId, bankAssetAccount.getIncreaseDirection(),
				amount);
		liabilityRec = new LedgerTransactionEntryRecord(accountId, liabilityAccount.getIncreaseDirection(), amount);

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(assetRec, liabilityRec), "Deposit");
		createTransactionUseCase.createTransactionUseCase(traRec);

	}

	@Override
	public void createWithdrawUseCase(UUID accountId, UUID bankAssetAccountId, BigDecimal amount) throws Exception {
		LedgerTransactionEntryRecord assetRec;
		LedgerTransactionEntryRecord liabilityRec;
		Account bankAssetAccount = findAccountByIdUseCase.findAccountById(bankAssetAccountId);
		Account liabilityAccount = findAccountByIdUseCase.findAccountById(accountId);
		if (liabilityAccount.getNormalType() != NormalType.NORMAL_CREDIT_ACCOUNT) {
			throw new Exception("Not a liabiliy account");
		}

		assetRec = new LedgerTransactionEntryRecord(bankAssetAccountId, bankAssetAccount.getDecreaseDirection(),
				amount);
		liabilityRec = new LedgerTransactionEntryRecord(accountId, liabilityAccount.getDecreaseDirection(), amount);

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(assetRec, liabilityRec), "Deposit");
		createTransactionUseCase.createTransactionUseCase(traRec);

	}

	@Override
	public void createSelfDepositUseCase(UUID bankAssetAccountId, BigDecimal amount) throws Exception {
		LedgerTransactionEntryRecord assetRec;
		LedgerTransactionEntryRecord liabilityRec;
		Long userId = userIdPort.userId();
		Account own = findAccountByOwnerIdUseCase.findAccountByOwnerIdUseCase(userId);

		Account bankAssetAccount = findAccountByIdUseCase.findAccountById(bankAssetAccountId);
		Account liabilityAccount = findAccountByIdUseCase.findAccountById(own.getId());
		if (liabilityAccount.getNormalType() != NormalType.NORMAL_CREDIT_ACCOUNT) {
			throw new Exception("Not a liabiliy account");
		}

		assetRec = new LedgerTransactionEntryRecord(bankAssetAccountId, bankAssetAccount.getIncreaseDirection(),
				amount);
		liabilityRec = new LedgerTransactionEntryRecord(own.getId(), liabilityAccount.getIncreaseDirection(), amount);

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(assetRec, liabilityRec), "Deposit");
		createTransactionUseCase.createTransactionUseCase(traRec);

	}

	@Override
	public void createSelfWithdrawUseCase(UUID bankAssetAccountId, BigDecimal amount) throws Exception {
		LedgerTransactionEntryRecord assetRec;
		LedgerTransactionEntryRecord liabilityRec;
		Long userId = userIdPort.userId();
		Account own = findAccountByOwnerIdUseCase.findAccountByOwnerIdUseCase(userId);

		Account bankAssetAccount = findAccountByIdUseCase.findAccountById(bankAssetAccountId);
		Account liabilityAccount = findAccountByIdUseCase.findAccountById(own.getId());
		if (liabilityAccount.getNormalType() != NormalType.NORMAL_CREDIT_ACCOUNT) {
			throw new Exception("Not a liabiliy account");
		}

		assetRec = new LedgerTransactionEntryRecord(bankAssetAccountId, bankAssetAccount.getDecreaseDirection(),
				amount);
		liabilityRec = new LedgerTransactionEntryRecord(own.getId(), liabilityAccount.getDecreaseDirection(), amount);

		LedgerTransactionRecord traRec = new LedgerTransactionRecord(List.of(assetRec, liabilityRec), "Deposit");
		createTransactionUseCase.createTransactionUseCase(traRec);

	}
}
