package com.ledger.ledgermicroservice.transaction.adapter.outbound.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
import lombok.Setter;

//Aggregate root containing the ledger entries

@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionPersistanceModel {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	@Column(nullable = false, unique = true)
	private UUID transactionId;

	@Getter
	@Column(nullable = false)
	private String description;

	@Getter
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionEntryPersistenceModel> entries = new ArrayList<TransactionEntryPersistenceModel>();

	public List<TransactionEntryPersistenceModel> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	public TransactionPersistanceModel(String desc, LocalDateTime createdAt) {
		this.description = desc;
		this.createdAt = createdAt;

	}

	public void addEntry(TransactionEntryPersistenceModel entr) {
		entries.add(entr);
		entr.setTransaction(this);
	}

}
