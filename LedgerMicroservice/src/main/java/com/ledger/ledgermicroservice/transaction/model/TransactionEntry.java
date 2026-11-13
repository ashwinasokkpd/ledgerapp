package com.ledger.ledgermicroservice.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TransactionEntry {
	private UUID entryId;

	private LocalDateTime createdAt;
	private UUID accountId;

	private Direction direction;
	@Setter
	private UUID transactionId;

	private BigDecimal amount;

	TransactionEntry() {
	}

	public static class LedgerEntryBuilder {
		private UUID id;
		private LocalDateTime createdAt;
		private UUID accountId;
		private Direction direction;
		private UUID transactionId;
		private BigDecimal amount;

		public LedgerEntryBuilder setId(UUID id) {
			this.id = id;
			return this;
		}

		public LedgerEntryBuilder generateNewId() {
			id = UUID.randomUUID();
			return this;
		}

		public LedgerEntryBuilder setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public LedgerEntryBuilder now() {
			createdAt = LocalDateTime.now();
			return this;
		}

		public LedgerEntryBuilder setAccountId(UUID accountId) {
			this.accountId = accountId;
			return this;
		}

		public LedgerEntryBuilder setDirection(Direction direction) {
			this.direction = direction;
			return this;
		}

		public LedgerEntryBuilder setTransactionId(UUID transactionId) {
			this.transactionId = transactionId;
			return this;
		}

		public LedgerEntryBuilder setAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionEntry build() throws Exception {
			TransactionEntry entry = new TransactionEntry();
			entry.entryId = id;
			entry.createdAt = createdAt;
			entry.accountId = accountId;
			entry.direction = direction;
			entry.transactionId = transactionId;
			entry.amount = amount;
			if (entry.direction == null)
				throw new Exception("Direction Missing");
			return entry;
		}

	}

	public static LedgerEntryBuilder builder() {
		return new LedgerEntryBuilder();
	}
}
