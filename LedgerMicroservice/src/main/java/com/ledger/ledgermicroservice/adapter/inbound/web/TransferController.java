package com.ledger.ledgermicroservice.adapter.inbound.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ledger.ledgermicroservice.application.ports.inbound.CreateDepositUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfDepositUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateSelfWithdrawUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateTransferUseCase;
import com.ledger.ledgermicroservice.application.ports.inbound.CreateWithdrawUseCase;
import com.ledger.ledgermicroservice.domain.model.AmountRecord;
import com.ledger.ledgermicroservice.domain.model.DepositWithdrawRecord;
import com.ledger.ledgermicroservice.domain.model.SelfTransferRecord;
import com.ledger.ledgermicroservice.domain.model.TransferRecord;

@RestController
@RequestMapping("transfer")
public class TransferController {
	private final CreateTransferUseCase createTransferUseCase;
	private final CreateSelfTransferUseCase createSelfTransferUseCase;
	private final CreateDepositUseCase createDepositUseCase;
	private final CreateWithdrawUseCase createWithdrawUseCase;
	private final CreateSelfDepositUseCase createSelfDepositUseCase;
	private final CreateSelfWithdrawUseCase createSelfWithdrawUseCase;

	private final UUID bankAssetAccountId;

	public TransferController(CreateTransferUseCase createTransferUseCase,
			CreateSelfTransferUseCase createSelfTransferUseCase, CreateDepositUseCase createDepositUseCase,
			CreateWithdrawUseCase createWithdrawUseCase, CreateSelfDepositUseCase createSelfDepositUseCase,
			CreateSelfWithdrawUseCase createSelfWithdrawUseCase,
			@Value("${bank.asset.accountid}") UUID bankAssetAccountId) {
		super();
		this.createTransferUseCase = createTransferUseCase;
		this.createSelfTransferUseCase = createSelfTransferUseCase;
		this.createDepositUseCase = createDepositUseCase;
		this.createWithdrawUseCase = createWithdrawUseCase;
		this.createSelfDepositUseCase = createSelfDepositUseCase;
		this.createSelfWithdrawUseCase = createSelfWithdrawUseCase;
		this.bankAssetAccountId = bankAssetAccountId;
	}

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

	@PostMapping("deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositWithdrawRecord rec) {
		try {
			createDepositUseCase.createDepositUseCase(rec.depositorId(), bankAssetAccountId, rec.amount());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("withdraw")
	public ResponseEntity<String> withdraw(@RequestBody DepositWithdrawRecord rec) {
		try {
			createWithdrawUseCase.createWithdrawUseCase(rec.depositorId(), bankAssetAccountId, rec.amount());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("self-deposit")
	public ResponseEntity<String> selfDeposit(@RequestBody AmountRecord amount) {
		try {
			createSelfDepositUseCase.createSelfDepositUseCase(bankAssetAccountId, amount.amount());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("self-withdraw")
	public ResponseEntity<String> selfWithdraw(@RequestBody AmountRecord amount) {
		try {
			createSelfWithdrawUseCase.createSelfWithdrawUseCase(bankAssetAccountId, amount.amount());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}
}
