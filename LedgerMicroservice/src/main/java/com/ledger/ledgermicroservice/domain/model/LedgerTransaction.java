package com.ledger.ledgermicroservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

//Aggregate root containing the ledger entries

@Getter
public class LedgerTransaction {
	private UUID transactionId;

	private String description;

	private LocalDateTime createdAt;

	private List<LedgerTransactionEntry> entries = new ArrayList<LedgerTransactionEntry>();

	public List<LedgerTransactionEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	LedgerTransaction() {
	}

	public void addEntry(LedgerTransactionEntry entr) {
		entries.add(entr);
		entr.setTransactionId(this.transactionId);
	}

	public void validate() throws Exception {
		BigDecimal balance = BigDecimal.ZERO;

		for (LedgerTransactionEntry entr : entries) {

			balance = entr.getDirection() == LedgerDirection.CREDIT ? balance.add(entr.getAmount())
					: balance.subtract(entr.getAmount());
		}
		if (entries.size() < 2 || balance.compareTo(BigDecimal.ZERO) != 0) {
			throw new Exception("Unbalanced Transaction");
		}

	}

	public static class TransactionBuilder {
		String desc;
		List<LedgerTransactionEntry> entries = new ArrayList<>();
		LocalDateTime createdAt;
		UUID id;

		public TransactionBuilder setDesc(String desc) {
			this.desc = desc;
			return this;
		}

		public TransactionBuilder setcreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public TransactionBuilder now() {
			this.createdAt = LocalDateTime.now();
			return this;
		}

		public TransactionBuilder setId(UUID id) {
			this.id = id;
			return this;
		}

		public TransactionBuilder genenrateNewId() {
			this.id = UUID.randomUUID();
			return this;
		}

		public TransactionBuilder addEntry(LedgerTransactionEntry entr) throws Exception {
//			if (this.id == null)
//				throw new Exception("Id not set");
			entries.add(entr);
			entr.setTransactionId(this.id);
			return this;
		}

		public LedgerTransaction build() throws Exception {
			LedgerTransaction trans = new LedgerTransaction();
			trans.transactionId = id;
			trans.description = desc;
			trans.createdAt = createdAt;
			trans.entries = entries;
			trans.validate();
			return trans;
		}

	}

	public static TransactionBuilder builder() {
		return new TransactionBuilder();
	}

}
