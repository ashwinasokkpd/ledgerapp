package com.ledger.ledgermicroservice.application.ports.outbound;

import com.ledger.ledgermicroservice.shared.util.ConsumerWithException;

public interface TransactionalPort {
	<T, E extends Exception> void doTransactionally(ConsumerWithException<T, E> func, T in) throws E;
}
