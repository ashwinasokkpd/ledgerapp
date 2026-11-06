package com.ledger.ledgermicroservice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.TransactionRepo.TransactionRepoInterface;
import com.ledger.ledgermicroservice.domain.model.Account;

record AccountRecord(String accountName, Account.AccountType accountType, Long ownerId) {
}

@RestController
public class LedgerController {
	private final TransactionService serv;
	private final AccountRepo accountRepo;

	public LedgerController(TransactionService serv, AccountRepo accountRepo) {
		this.serv = serv;
		this.accountRepo = accountRepo;
	}

	@PostMapping("/transaction")
	void addTrasaction(@RequestBody TransactionRecord rec) {
		serv.addTransaction(rec);
	}

	@GetMapping("/transaction")
	ResponseEntity<List<TransactionRepoInterface>> getAllTransactions() {
		return ResponseEntity.ok(serv.getAllTransactions());
	}

	@GetMapping("/transaction/{id}")
	ResponseEntity<TransactionRepoInterface> getTransactionWithId(@PathVariable Long id) {
		return ResponseEntity.ok(serv.getTransactionWithId(id));
	}

	@GetMapping("/account")
	ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.ok(accountRepo.findAll());
	}

	@PostMapping("/account")
	void addAccount(@RequestBody AccountRecord acc) {
		Account account = new Account(acc.accountName(), acc.accountType(), acc.ownerId());
		accountRepo.save(account);
	}
}
