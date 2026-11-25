package com.ledger.ledgermicroservice.shared.util;

public interface ConsumerWithException<T, E extends Exception> {
	public void accept(T in) throws E;
}
