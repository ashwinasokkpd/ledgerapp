package com.ledger.ledgermicroservice.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

//Aggregate root containing the ledger entries

@Getter
public class Transaction {
	private UUID transactionId;

	private String description;

	private LocalDateTime createdAt;

	private List<TransactionEntry> entries = new ArrayList<TransactionEntry>();

	public List<TransactionEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	Transaction() {
	}

	public void addEntry(TransactionEntry entr) {
		entries.add(entr);
		entr.setTransactionId(this.transactionId);
	}

	public void validate() throws Exception {
		BigDecimal balance = BigDecimal.ZERO;

		for (TransactionEntry entr : entries) {

			balance = entr.getDirection() == Direction.CREDIT ? balance.add(entr.getAmount())
					: balance.subtract(entr.getAmount());
		}
		if (entries.size() < 2 || balance.compareTo(BigDecimal.ZERO) != 0) {
			throw new Exception("Unbalanced Transaction");
		}

	}

	public static class TransactionBuilder {
		String desc;
		List<TransactionEntry> entries = new ArrayList<>();
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

		public TransactionBuilder createdNow() {
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

		public TransactionBuilder addEntry(TransactionEntry entr) throws Exception {
			if (this.id == null)
				throw new Exception("Id not set");
			entries.add(entr);
			entr.setTransactionId(this.id);
			return this;
		}

		public Transaction build() throws Exception {
			Transaction trans = new Transaction();
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
