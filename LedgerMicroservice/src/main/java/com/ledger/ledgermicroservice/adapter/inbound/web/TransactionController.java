package com.ledger.ledgermicroservice.adapter.inbound.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransactionUseCase;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionRecord;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("transaction")
@AllArgsConstructor
public class TransactionController {
	private final CreateTransactionUseCase createTransactionUseCase;

	@PostMapping()
	ResponseEntity<String> createTransaction(@RequestBody LedgerTransactionRecord rec) throws Exception {
		try {
			createTransactionUseCase.createTransactionUseCase(rec);
			return ResponseEntity.ok("Transaction Succeed");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>("Transaction Failed(" + ex.getMessage() + ")", HttpStatus.BAD_REQUEST);
		}

	}
}
