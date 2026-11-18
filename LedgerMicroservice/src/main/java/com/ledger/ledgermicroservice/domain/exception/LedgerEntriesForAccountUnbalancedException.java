package com.ledger.ledgermicroservice.domain.exception;

public class LedgerEntriesForAccountUnbalancedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LedgerEntriesForAccountUnbalancedException(String error) {
		super(error);
	}
}
