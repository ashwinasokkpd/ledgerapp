package com.ledger.ledgermicroservice.domain.exception;

public class UnbalancedTransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnbalancedTransactionException(String error) {
		super(error);
	}
}
