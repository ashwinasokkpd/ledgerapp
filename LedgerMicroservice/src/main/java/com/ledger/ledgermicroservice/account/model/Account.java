package com.ledger.ledgermicroservice.account.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private UUID id;
	private String name;
	private AccountType type;
	private Long ownerId;

	private static List<AccountType> normalDebitAccounts = List.of(AccountType.ASSET, AccountType.EXPENSE,
			AccountType.DIVIDEND);

	public NormalType getNormalType() {
		return (normalDebitAccounts.contains(type)) ? NormalType.NORMAL_DEBIT_ACCOUNT
				: NormalType.NORMAL_CREDIT_ACCOUNT;

	}

	public static enum AccountType {
		ASSET, EXPENSE, DIVIDEND, LIABILITY, REVENUE, EQUITY
	}

	public static enum NormalType {
		NORMAL_DEBIT_ACCOUNT, NORMAL_CREDIT_ACCOUNT
	}

	public static Account createUsing(String name, AccountType type, Long ownerId) {
		return new Account(UUID.randomUUID(), name, type, ownerId);
	}

}
