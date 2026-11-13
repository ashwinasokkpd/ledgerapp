package com.ledger.ledgermicroservice.transaction.adapter.inbound;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.transaction.model.Transaction;
import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;
import com.ledger.ledgermicroservice.transaction.model.TransactionRecord;
import com.ledger.ledgermicroservice.transaction.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetAllTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetAllTransactionsUseCase;
import com.ledger.ledgermicroservice.transaction.ports.inbound.GetTransactionWithUUID;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	private final CreateTransactionUseCase createTransactionUseCase;
	private final GetAllTransactionsUseCase getAllTransactionsUseCase;
	private final GetTransactionWithUUID getTransactionWithUUID;
	private final GetAllTransactionEntriesForAccountFromTo getAllTransactionEntriesForAccountFromTo;

	TransactionController(CreateTransactionUseCase createTransactionUseCase,
			GetAllTransactionsUseCase getAllTransactionsUseCase, GetTransactionWithUUID getTransactionWithUUID,
			GetAllTransactionEntriesForAccountFromTo getAllTransactionEntriesForAccountFromTo) {
		this.createTransactionUseCase = createTransactionUseCase;
		this.getAllTransactionsUseCase = getAllTransactionsUseCase;
		this.getTransactionWithUUID = getTransactionWithUUID;
		this.getAllTransactionEntriesForAccountFromTo = getAllTransactionEntriesForAccountFromTo;
	}

	@PostMapping()
	void addTrasaction(@RequestBody TransactionRecord rec) throws Exception {
		createTransactionUseCase.createTransactionUseCase(rec);
	}

	@GetMapping()
	ResponseEntity<List<Transaction>> getAllTrasaction() throws Exception {
		try {
			return new ResponseEntity<>(getAllTransactionsUseCase.getAllTransactionsUseCase(), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

	@GetMapping("/{uuid}")
	ResponseEntity<Transaction> getTrasactionWithUUID(@PathVariable UUID uuid) throws Exception {
		try {
			return new ResponseEntity<>(getTransactionWithUUID.getTransactionWithUUID(uuid), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

	@GetMapping("account/{uuid}")
	ResponseEntity<List<TransactionEntry>> getAllTrasactionEntriesFromTo(@PathVariable UUID accountId,
			@RequestParam LocalDateTime from, @RequestParam LocalDateTime to) throws Exception {
		try {
			return new ResponseEntity<>(getAllTransactionEntriesForAccountFromTo
					.getAllTransactionEntriesForAccountFromTo(accountId, from, to), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

}
