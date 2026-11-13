package com.ledger.ledgermicroservice.transaction.adapter.outbound.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ledger.ledgermicroservice.transaction.model.Direction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ledger_entries")
public class TransactionEntryPersistenceModel {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	@Column(nullable = false)
	private UUID accountId;

	@Getter
	@Setter
	@Column(nullable = false, unique = true)
	private UUID entryId;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Direction direction;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "transaction_id")
	TransactionPersistanceModel transaction;

	@Getter
	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	TransactionEntryPersistenceModel() {
	}

	public TransactionEntryPersistenceModel(UUID account, Direction direction, BigDecimal amount, LocalDateTime createdAt) {
		this.accountId = account;
		this.direction = direction;
		this.amount = amount;
		this.createdAt = createdAt;
	}

}
