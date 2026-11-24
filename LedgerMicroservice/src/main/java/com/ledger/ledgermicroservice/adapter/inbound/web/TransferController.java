package com.ledger.ledgermicroservice.adapter.inbound.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;
import com.ledger.ledgermicroservice.domain.model.TransferRecord;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("transfer")
@AllArgsConstructor
public class TransferController {
	private CreateTransferUseCase createTransferUseCase;
	private CreateSelfTransferUseCase createSelfTransferUseCase;

	@PostMapping()
	public ResponseEntity<String> transfer(@RequestBody TransferRecord rec) {
		try {
			createTransferUseCase.createTransferUseCase(rec);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("self-transfer")
	public ResponseEntity<String> selfTransfer(@RequestBody SelfTransferRecord rec) {
		try {
			createSelfTransferUseCase.createSelfTransferUseCase(rec);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}

}
