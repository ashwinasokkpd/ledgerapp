package com.ledger.ledgermicroservice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.LedgerEntry.DebitCredit;
import com.ledger.ledgermicroservice.domain.model.Transaction;
import com.ledger.ledgermicroservice.domain.model.Account.AccountType;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	@EntityGraph(attributePaths = { "entries", "entries.account" })
	<T> T findProjectedById(Long id, Class<T> klass);

	@EntityGraph(attributePaths = { "entries", "entries.account" })
	List<TransactionRepoInterface> findProjectedBy();

	public static interface TransactionRepoInterface {
		Long getId();

		String getDescription();

		LocalDateTime getCreatedAt();

		@Value("#{target.entries}")
		List<Entries> getEntries();

		public static interface Entries {
			@Value("#{target.account.name}")
			String getAccountName();

			@Value("#{target.account.type}")
			AccountType getAccountType();

			@Value("#{target.type}")
			DebitCredit getType();

			@Value("#{target.amount}")
			BigDecimal getAmount();
		}
	}
}
