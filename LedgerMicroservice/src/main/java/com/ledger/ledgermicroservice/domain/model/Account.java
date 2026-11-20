package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ledger.ledgermicroservice.domain.exception.InsufficientFundException;
import com.ledger.ledgermicroservice.domain.exception.LedgerEntriesForAccountUnbalancedException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {

	private UUID id;
	private String name;
	private AccountType type;
	private Long ownerId;
	private BigDecimal balance;
	private LocalDateTime lastAt;

	public static enum AccountType {
		ASSET, EXPENSE, DIVIDEND, LIABILITY, REVENUE, EQUITY
	}

	private static List<AccountType> normalDebitAccounts = List.of(AccountType.ASSET, AccountType.EXPENSE,
			AccountType.DIVIDEND);

	public NormalType getNormalType() {
		return (normalDebitAccounts.contains(type)) ? NormalType.NORMAL_DEBIT_ACCOUNT
				: NormalType.NORMAL_CREDIT_ACCOUNT;

	}

	public Account(UUID id, String name, AccountType type, Long ownerId, BigDecimal balance, LocalDateTime lastAt) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.ownerId = ownerId;
		this.balance = balance;
		this.lastAt = lastAt;

	}

	public void doCredit(BigDecimal amount, BigDecimal delta) throws Exception {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Amount is negative");
		}

		amount = getNormalType() == NormalType.NORMAL_DEBIT_ACCOUNT ? amount.negate() : amount;
		balance = balance.add(amount).add(delta);

		if (balance.compareTo(BigDecimal.ZERO) < 0) {
			balance = balance.subtract(delta);
			balance = balance.subtract(amount);
			throw new InsufficientFundException(name + "(" + id + ")" + "does not have sufficient balance");
		}
		balance = balance.subtract(delta);
	}

	public void doDebit(BigDecimal amount, BigDecimal delta) throws Exception {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Amount is negative");
		}
		amount = getNormalType() == NormalType.NORMAL_DEBIT_ACCOUNT ? amount : amount.negate();
		balance = balance.add(amount).add(delta);
		if (balance.compareTo(BigDecimal.ZERO) < 0) {
			balance = balance.subtract(delta);
			balance = balance.subtract(amount);
			throw new InsufficientFundException(name + "(" + id + ")" + "does not have sufficient balance");
		}
		balance = balance.subtract(delta);
	}

	public void addDelta(BigDecimal delta, LocalDateTime asOf) throws Exception {
		if (delta.compareTo(BigDecimal.ZERO) < 0) {
			throw new LedgerEntriesForAccountUnbalancedException("Balance is below zero");
		}
		balance = balance.add(delta);
		lastAt = asOf;
	}

	public static Account createUsing(String name, AccountType type, Long ownerId, BigDecimal balance, UUID accountUUID,
			LocalDateTime lastAt) {
		return new Account(accountUUID, name, type, ownerId, balance, lastAt);
	}

	public static Account createUsing(String name, AccountType type, Long ownerId, BigDecimal balance,
			LocalDateTime lastAt) {
		return new Account(null, name, type, ownerId, balance, lastAt);
	}

	public static enum NormalType {
		NORMAL_DEBIT_ACCOUNT, NORMAL_CREDIT_ACCOUNT
	}

}
