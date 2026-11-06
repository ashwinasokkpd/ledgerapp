package com.ledger.ledgermicroservice.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor

public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "account_name", nullable = false, unique = true)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType type;

	@Column(name = "owner_Id", nullable = false)
	private Long ownerId;

	private static List<AccountType> normalDebitAccounts = List.of(AccountType.ASSET, AccountType.EXPENSE,
			AccountType.DIVIDEND);

	public Account(String name, AccountType type, Long ownerId) {
		this.name = name;
		this.type = type;
		this.ownerId = ownerId;
	}

	public NormalType getNormalType() {
		if (normalDebitAccounts.contains(type))
			return NormalType.NORMAL_DEBIT_ACCOUNT;
		return NormalType.NORMAL_CREDIT_ACCOUNT;

	}

	public static enum AccountType {
		ASSET, EXPENSE, DIVIDEND, LIABILITY, REVENUE, EQUITY
	}

	public static enum NormalType {
		NORMAL_DEBIT_ACCOUNT, NORMAL_CREDIT_ACCOUNT
	}
  

}
