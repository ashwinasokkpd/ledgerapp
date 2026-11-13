package com.ledger.ledgermicroservice.account.adapter.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.account.adapter.outbound.entity.AccountPersistanceModel;

public interface AccountRepo extends JpaRepository<AccountPersistanceModel, Long> {

}
