package com.ledger.ledgermicroservice.adapter.outbound.persistance;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ledger.ledgermicroservice.application.ports.outbound.TransactionalPort;
import com.ledger.ledgermicroservice.shared.util.ConsumerWithException;

@Component
public class TransactionalAdapter implements TransactionalPort {

	@Override
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public <T, E extends Exception> void doTransactionally(ConsumerWithException<T, E> func, T in) throws E {
		func.accept(in);
	}

}
