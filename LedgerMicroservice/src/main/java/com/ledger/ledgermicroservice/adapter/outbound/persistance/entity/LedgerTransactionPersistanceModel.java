package com.ledger.ledgermicroservice.adapter.outbound.persistance.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class LedgerTransactionPersistanceModel {
	@Getter
	@Id
	@UuidGenerator(style = UuidGenerator.Style.VERSION_7)
	private UUID id;

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
	private List<LedgerTransactionEntryPersistenceModel> entries = new ArrayList<LedgerTransactionEntryPersistenceModel>();

	public List<LedgerTransactionEntryPersistenceModel> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	public LedgerTransactionPersistanceModel(String desc, LocalDateTime createdAt) {
		this.description = desc;
		this.createdAt = createdAt;

	}

	public void addEntry(LedgerTransactionEntryPersistenceModel entr) {
		entries.add(entr);
		entr.setTransaction(this);
	}

}
