package com.ledger.ledgermicroservice.adapter.inbound.web;

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

import com.ledger.ledgermicroservice.application.ports.inbound.CreateLedgerTransactionUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionsUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.GetLedgerTransactionWithUUID;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

@RestController
@RequestMapping("ledger-transaction")
public class LedgerTransactionController {
	private final CreateLedgerTransactionUseCase createLedgerTransactionUseCase;
	private final GetAllLedgerTransactionsUseCase getAllLedgerTransactionsUseCase;
	private final GetLedgerTransactionWithUUID getLedgerTransactionWithUUID;
	private final GetAllLedgerTransactionEntriesForAccountFromTo getAllLedgerTransactionEntriesForAccountFromTo;

	LedgerTransactionController(CreateLedgerTransactionUseCase createTransactionUseCase,
			GetAllLedgerTransactionsUseCase getAllLedgerTransactionsUseCase,
			GetLedgerTransactionWithUUID getLedgerTransactionWithUUID,
			GetAllLedgerTransactionEntriesForAccountFromTo getAllLedgerTransactionEntriesForAccountFromTo) {
		this.createLedgerTransactionUseCase = createTransactionUseCase;
		this.getAllLedgerTransactionsUseCase = getAllLedgerTransactionsUseCase;
		this.getLedgerTransactionWithUUID = getLedgerTransactionWithUUID;
		this.getAllLedgerTransactionEntriesForAccountFromTo = getAllLedgerTransactionEntriesForAccountFromTo;
	}

	@PostMapping()
	void addTrasaction(@RequestBody LedgerTransactionRecord rec) throws Exception {
		createLedgerTransactionUseCase.createLedgerTransactionUseCase(rec);
	}

	@GetMapping()
	ResponseEntity<List<LedgerTransaction>> getAllTrasaction() throws Exception {
		try {
			return new ResponseEntity<>(getAllLedgerTransactionsUseCase.getAllTransactionsUseCase(), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

	@GetMapping("/{uuid}")
	ResponseEntity<LedgerTransaction> getTrasactionWithUUID(@PathVariable UUID uuid) throws Exception {
		try {
			return new ResponseEntity<>(getLedgerTransactionWithUUID.getTransactionWithUUID(uuid), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

	@GetMapping("account/{uuid}")
	ResponseEntity<List<LedgerTransactionEntry>> getAllTrasactionEntriesFromTo(@PathVariable UUID accountId,
			@RequestParam LocalDateTime from, @RequestParam LocalDateTime to) throws Exception {
		try {
			return new ResponseEntity<>(getAllLedgerTransactionEntriesForAccountFromTo
					.getAllTransactionEntriesForAccountFromTo(accountId, from, to), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

}
