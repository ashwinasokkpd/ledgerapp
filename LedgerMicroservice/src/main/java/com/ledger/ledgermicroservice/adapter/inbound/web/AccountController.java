package com.ledger.ledgermicroservice.adapter.inbound.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateAccountUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.FindAccountByOwnerIdUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllAccountsUseCase;
import com.ledger.ledgermicroservice.domain.model.Account;

import lombok.AllArgsConstructor;

@RequestMapping("/account")
@RestController
@AllArgsConstructor
public class AccountController {
	private final CreateAccountUseCase createAccountUseCase;
	private final GetAllAccountsUseCase getAllAccountsUseCase;
	private final FindAccountByIdUseCase findAccountByIdUseCase;
	private final FindAccountByOwnerIdUseCase findAccountByOwnerIdUseCase;

	@GetMapping
	ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.ok(getAllAccountsUseCase.getAllAccounts());
	}

	@PostMapping
	void addAccount(@RequestBody CreateAccountUseCase.AccountDataHolder acc) {
		createAccountUseCase.createAccount(acc);
	}

	@GetMapping("/{id}")
	ResponseEntity<Account> findAccountById(@PathVariable UUID id) {
		return ResponseEntity.ok(findAccountByIdUseCase.findAccountById(id));
	}

	@GetMapping("/from-ownerid/{id}")
	ResponseEntity<Account> findAccountByOwnerId(@PathVariable Long id) {
		return ResponseEntity.ok(findAccountByOwnerIdUseCase.findAccountByOwnerIdUseCase(id));
	}

}
