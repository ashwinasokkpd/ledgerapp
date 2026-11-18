package com.ledger.ledgermicroservice.adapter.outbound.persistance.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.LedgerTransactionEntryPersistenceModel;
import com.ledger.ledgermicroservice.domain.model.AccountDeltaRecord;

public interface LedgerTransactionEntryRepo extends JpaRepository<LedgerTransactionEntryPersistenceModel, UUID> {
	List<LedgerTransactionEntryPersistenceModel> findByAccountIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(
			UUID uuid, LocalDateTime from, LocalDateTime to);

	/*
	 * SELECT COALESCE(SUM(CASE WHEN e.direction='DEBIT' THEN e.amount ELSE
	 * -e.amount END),0) as delta, e.account_id, MAX(e.created_at) as latest_at FROM
	 * ledger_entries as e RIGHT JOIN UNNEST(CAST(ARRAY['2026-11-16
	 * 19:28:39.396387'] as
	 * timestamp[]),CAST(ARRAY['e70fe4c5-b585-45a8-901f-ffecd41a42ea']as uuid[])) as
	 * va(created_at,account_id) ON e.created_at >=va.created_at AND
	 * e.account_id=va.account_id GROUP BY e.account_id ;
	 */
	@Query(value = "SELECT va.account_id,COALESCE(SUM(CASE WHEN e.direction='DEBIT' THEN e.amount ELSE -e.amount END),0) as delta, "
			+ " MAX(e.created_at) as latest_at FROM ledger_entries as e "
			+ "RIGHT JOIN UNNEST(CAST(:createdAts as timestamp[]),CAST(:accountIds as uuid[])) "
			+ "as va(created_at,account_id) "
			+ "ON  e.account_id=va.account_id AND (va.created_at IS NULL OR e.created_at >=va.created_at)   GROUP BY va.account_id ", nativeQuery = true)
	List<AccountDeltaRecord> getAccountDeltaFor(@Param("createdAts") LocalDateTime[] createdAts,
			@Param("accountIds") UUID[] accountIds);
}
