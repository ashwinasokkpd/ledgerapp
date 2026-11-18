package com.ledger.ledgermicroservice.domain.exception;

public class InsufficientFundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientFundException(String error) {
		super(error);
	}

}
