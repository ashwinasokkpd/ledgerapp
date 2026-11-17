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
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllAccountsUseCase;
import com.ledger.ledgermicroservice.domain.model.Account;

@RequestMapping("/account")
@RestController
public class AccountController {
	private final CreateAccountUseCase createAccountUseCase;
	private final GetAllAccountsUseCase getAllAccountsUseCase;
	private final FindAccountByIdUseCase findAccountByIdUseCase;

	public AccountController(CreateAccountUseCase createAccountUseCase, GetAllAccountsUseCase getAllAccountsUseCase,
			FindAccountByIdUseCase findAccountByIdUseCase) {
		this.createAccountUseCase = createAccountUseCase;
		this.getAllAccountsUseCase = getAllAccountsUseCase;
		this.findAccountByIdUseCase = findAccountByIdUseCase;
	}

	@GetMapping

	ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.ok(getAllAccountsUseCase.getAllAccounts());
	}

	@GetMapping("/{id}")

	ResponseEntity<Account> findAccountById(@PathVariable UUID id) {
		return ResponseEntity.ok(findAccountByIdUseCase.findAccountById(id));
	}

	@PostMapping
	void addAccount(@RequestBody CreateAccountUseCase.AccountDataHolder acc) {
		createAccountUseCase.createAccount(acc);
	}
}
