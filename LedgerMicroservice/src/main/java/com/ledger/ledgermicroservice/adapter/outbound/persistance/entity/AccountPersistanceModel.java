package com.ledger.ledgermicroservice.adapter.outbound.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ledger.ledgermicroservice.domain.model.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator(style = UuidGenerator.Style.VERSION_7)
	private UUID id;

	@Column(name = "account_name", nullable = false, unique = true)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private Account.AccountType type;

	@Column(name = "owner_Id", nullable = false)
	private Long ownerId;

	@Column(nullable = false)
	private BigDecimal balance;

	@Column(nullable = true)
	private LocalDateTime lastAt;

	public AccountPersistanceModel(String name, Account.AccountType type, UUID uuid, Long ownerId, BigDecimal balance,
			LocalDateTime lastAt) {
		this.name = name;
		this.type = type;
		this.id = uuid;
		this.ownerId = ownerId;
		this.balance = balance;
		this.lastAt = lastAt;
	}

	public static AccountPersistanceModel fromDomain(Account acc) {
		AccountPersistanceModel persModel = new AccountPersistanceModel(acc.getName(), acc.getType(), acc.getId(),
				acc.getOwnerId(), acc.getBalance(), acc.getLastAt());
		return persModel;
	}

	public static Account toDomain(AccountPersistanceModel persModel) {
		return Account.createUsing(persModel.name, persModel.type, persModel.ownerId, persModel.balance, persModel.id);
	}

}
