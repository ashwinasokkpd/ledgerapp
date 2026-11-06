package com.ledger.ledgermicroservice.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ledger.ledgermicroservice.LedgerEntry;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Aggregate root containing the ledger entries

@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Column(name = "transaction_description", nullable = false)
	private String description;

	@Getter
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LedgerEntry> entries = new ArrayList<LedgerEntry>();

	public List<LedgerEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	public Transaction(String desc, LocalDateTime createdAt) {
		this.description = desc;
		this.createdAt = createdAt;

	}

	public void addEntry(LedgerEntry entr) {
		entries.add(entr);
		entr.setTransaction(this);
	}

}
