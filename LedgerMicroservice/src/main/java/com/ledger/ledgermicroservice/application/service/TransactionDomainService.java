package com.ledger.ledgermicroservice.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.outbound.AccountPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionEntryPersistancePort;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionPersistancePort;
import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.Account.NormalType;
import com.ledger.ledgermicroservice.domain.model.AccountDeltaRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerDirection;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction.TransactionBuilder;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntryRecord;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransactionDomainService implements CreateTransactionUseCase {
	private final LedgerTransactionPersistancePort ledgerTransactionPersistancePort;
	private final AccountPersistancePort accountPersistancePort;
	private final LedgerTransactionEntryPersistancePort ledgerTransactionEntryPersistancePort;

	@Override
	@Transactional
	public void createTransactionUseCase(LedgerTransactionRecord rec) throws Exception {
		List<UUID> accountList = rec.entries().stream().map(LedgerTransactionEntryRecord::accountId).toList();
		// find and lock all accounts for this transaction
		Map<UUID, Account> accountMap = accountPersistancePort.findAndLockAllByUUID(accountList).stream()
				.collect(Collectors.toMap(e -> e.getId(), e -> e));

		accountMap.keySet().forEach(k -> System.out.println(k));
		rec.entries().forEach(e -> System.out.println(e.accountId()));
		List<LocalDateTime> lastList = accountMap.values().stream().map(Account::getLastAt).toList();
		accountList = accountMap.keySet().stream().toList();
		Map<UUID, AccountDeltaRecord> accountDeltaRecordMap = ledgerTransactionEntryPersistancePort
				.getAccountDeltaFor(lastList, accountList).stream().map(e -> {
					if (accountMap.get(e.accountUUID()).getNormalType() == NormalType.NORMAL_CREDIT_ACCOUNT) {
						e = new AccountDeltaRecord(e.accountUUID(), e.delta().negate(), e.latestAt());
					}
					return e;
				}).collect(Collectors.toMap(e -> e.accountUUID(), e -> e));

		/*
		 * Map<UUID, BigDecimal> accountTransTotalMap = rec.entries().stream()
		 * .collect(Collectors.groupingBy(e -> e.accountId(),
		 * Collectors.reducing(BigDecimal.ZERO, e -> e.direction() ==
		 * LedgerDirection.DEBIT ? e.amount() : e.amount().negate(), BigDecimal::add)));
		 */
		for (LedgerTransactionEntryRecord ent : rec.entries()) {
			Account acc = accountMap.get(ent.accountId());
			if (ent.direction() == LedgerDirection.CREDIT)
				acc.doCredit(ent.amount(), accountDeltaRecordMap.get(ent.accountId()).delta());
			else
				acc.doDebit(ent.amount(), accountDeltaRecordMap.get(ent.accountId()).delta());
		}

		TransactionBuilder builder = LedgerTransaction.builder().genenrateNewId().now().setDesc(rec.desc());
		for (LedgerTransactionEntryRecord entr : rec.entries()) {
			LedgerTransactionEntry lEntr = LedgerTransactionEntry.builder().generateNewId().now()
					.setAccountId(entr.accountId()).setDirection(entr.direction()).setAmount(entr.amount()).build();

			builder.addEntry(lEntr);
		}
		ledgerTransactionPersistancePort.save(builder.build());

		for (AccountDeltaRecord deltaRec : accountDeltaRecordMap.values()) {
			Account acc = accountMap.get(deltaRec.accountUUID());
			acc.addDelta(deltaRec.delta(), deltaRec.latestAt());
			accountPersistancePort.save(acc);
		}
	}

}
