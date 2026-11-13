package com.ledger.ledgermicroservice.account.adapter.outbound.entity;

import java.util.UUID;

import com.ledger.ledgermicroservice.account.model.Account;

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
@Table(name = "ledger_accounts")
@Getter
@Setter
@NoArgsConstructor

public class AccountPersistanceModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_uuid", unique = true, nullable = false)
	private UUID accountUUID;
	@Column(name = "account_name", nullable = false, unique = true)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private Account.AccountType type;

	@Column(name = "owner_Id", nullable = false)
	private Long ownerId;

	public AccountPersistanceModel(String name, Account.AccountType type, UUID uuid, Long ownerId) {
		this.name = name;
		this.type = type;
		this.accountUUID = uuid;
		this.ownerId = ownerId;
	}

	public static AccountPersistanceModel fromDomain(Account acc) {
		AccountPersistanceModel persModel = new AccountPersistanceModel(acc.getName(), acc.getType(), acc.getId(),
				acc.getOwnerId());
		return persModel;
	}

	public static Account toDomain(AccountPersistanceModel persModel) {
		return Account.createUsing(persModel.name, persModel.type, persModel.ownerId);
	}

}
