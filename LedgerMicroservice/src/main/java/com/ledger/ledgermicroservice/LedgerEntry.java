package com.ledger.ledgermicroservice;

import java.math.BigDecimal;

import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.Transaction;

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
public class LedgerEntry {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@ManyToOne(optional = false)
	@JoinColumn(name = "account")
	private Account account;

	@Getter
	@Enumerated(EnumType.STRING)
	@Column(name = "entry_type")
	private DebitCredit type;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "transaction_id")
	Transaction transaction;

	@Getter
	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	LedgerEntry() {
	}

	LedgerEntry(Account account, DebitCredit type, Transaction transaction, BigDecimal amount) {
		this.account = account;
		this.type = type;
		this.transaction = transaction;
		this.amount = amount;
	}

	public static enum DebitCredit {
		DEBIT, CREDIT
	}
}
