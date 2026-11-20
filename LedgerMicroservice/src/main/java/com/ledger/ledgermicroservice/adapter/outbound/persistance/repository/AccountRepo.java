package com.ledger.ledgermicroservice.adapter.outbound.persistance.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.AccountPersistanceModel;

import jakarta.persistence.LockModeType;

public interface AccountRepo extends JpaRepository<AccountPersistanceModel, UUID> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT a FROM AccountPersistanceModel a WHERE a.id IN :ids ORDER BY a.id ASC")
	public List<AccountPersistanceModel> findAndLockAllByUUID(@Param("ids") List<UUID> uuids);

	public Optional<AccountPersistanceModel> findByOwnerId(UUID id);

	public Optional<AccountPersistanceModel> findByOwnerId(Long id);
}
