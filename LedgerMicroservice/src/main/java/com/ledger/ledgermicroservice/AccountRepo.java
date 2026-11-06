package com.ledger.ledgermicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ledger.ledgermicroservice.domain.model.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
