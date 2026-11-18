package com.ledger.ledgermicroservice.adapter.outbound.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ledger.ledgermicroservice.domain.model.LedgerDirection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ledger_entries")
public class LedgerTransactionEntryPersistenceModel {
	@Getter
	@Setter
	@Id
	@UuidGenerator(style = UuidGenerator.Style.VERSION_7)
	private UUID id;

	@Getter
	@Setter
	@Column(nullable = false)
	private UUID accountId;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LedgerDirection direction;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "transaction_id")
	LedgerTransactionPersistanceModel transaction;

	@Getter
	@Setter
	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	LedgerTransactionEntryPersistenceModel() {
	}

	public LedgerTransactionEntryPersistenceModel(UUID account, LedgerDirection direction, BigDecimal amount,
			LocalDateTime createdAt) {
		this.accountId = account;
		this.direction = direction;
		this.amount = amount;
		this.createdAt = createdAt;
	}

}
