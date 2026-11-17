package com.ledger.ledgermicroservice.adapter.inbound.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.application.ports.inbound.GetAllLedgerTransactionEntriesForAccountFromTo;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

@RestController
@RequestMapping("transaction-entry")
public class LedgerTransactionEntryController {
	private final GetAllLedgerTransactionEntriesForAccountFromTo getAllTransactionEntriesForAccountFromTo;

	LedgerTransactionEntryController(GetAllLedgerTransactionEntriesForAccountFromTo getAllTransactionEntriesForAccountFromTo) {
		this.getAllTransactionEntriesForAccountFromTo = getAllTransactionEntriesForAccountFromTo;
	}

	@GetMapping("account/{uuid}")
	ResponseEntity<List<LedgerTransactionEntry>> getAllTrasactionEntriesFromTo(@PathVariable(name = "uuid") UUID accountId,
			@RequestParam LocalDateTime from, @RequestParam LocalDateTime to) throws Exception {
		try {
			return new ResponseEntity<>(getAllTransactionEntriesForAccountFromTo
					.getAllTransactionEntriesForAccountFromTo(accountId, from, to), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(null);
		}

	}

}
