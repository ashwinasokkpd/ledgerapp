package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

	public Account(UUID id, String name, AccountType type, Long ownerId, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.type = type;
		this.ownerId = ownerId;
		this.balance = balance;
	}

	public void doCredit(BigDecimal amount, BigDecimal delta) throws Exception {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new Exception("Amount is negative");
		}
		amount = getNormalType() == NormalType.NORMAL_DEBIT_ACCOUNT ? amount.negate() : amount;
		this.balance.add(amount).add(delta);
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			this.balance.subtract(amount);
			throw new Exception("Balance went below zero");
		}
	}

	public void doDebit(BigDecimal amount, BigDecimal delta) throws Exception {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new Exception("Amount is negative");
		}
		amount = getNormalType() == NormalType.NORMAL_DEBIT_ACCOUNT ? amount : amount.negate();
		this.balance.add(amount).add(delta);
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			this.balance.subtract(amount);
			throw new Exception("Balance went below zero");
		}
	}

	public void addDelta(BigDecimal delta, LocalDateTime asOf) throws Exception {
		if (delta.compareTo(BigDecimal.ZERO) < 0) {
			throw new Exception("Balance is below zero");
		}
		balance.add(delta);
		lastAt = asOf;
	}

	public static Account createUsing(String name, AccountType type, Long ownerId, BigDecimal balance) {
		return new Account(UUID.randomUUID(), name, type, ownerId, balance);
	}

	public static Account createUsing(String name, AccountType type, Long ownerId, BigDecimal balance,
			UUID accountUUID) {
		return new Account(accountUUID, name, type, ownerId, balance);
	}

	public static enum NormalType {
		NORMAL_DEBIT_ACCOUNT, NORMAL_CREDIT_ACCOUNT
	}

}
