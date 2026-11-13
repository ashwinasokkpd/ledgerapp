package com.ledger.ledgermicroservice.transaction.adapter.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.transaction.adapter.outbound.entity.TransactionEntryPersistenceModel;
import com.ledger.ledgermicroservice.transaction.adapter.outbound.entity.TransactionPersistanceModel;
import com.ledger.ledgermicroservice.transaction.adapter.outbound.repository.TransactionRepo;
import com.ledger.ledgermicroservice.transaction.model.Transaction;
import com.ledger.ledgermicroservice.transaction.model.TransactionEntry;
import com.ledger.ledgermicroservice.transaction.ports.outbound.TransactionPersistancePort;

@Component
public class TransactionPersistanceAdapter implements TransactionPersistancePort {
	private final TransactionRepo repo;

	public TransactionPersistanceAdapter(TransactionRepo repo) {
		this.repo = repo;
	}

	@Override
	public void save(Transaction trs) {
		TransactionPersistanceModel transPers = new TransactionPersistanceModel(trs.getDescription(),
				trs.getCreatedAt());
		transPers.setTransactionId(trs.getTransactionId());
		for (TransactionEntry ent : trs.getEntries()) {
			TransactionEntryPersistenceModel lEntr = new TransactionEntryPersistenceModel(ent.getAccountId(),
					ent.getDirection(), ent.getAmount(), ent.getCreatedAt());
			lEntr.setEntryId(ent.getEntryId());
			transPers.addEntry(lEntr);
		}
		repo.save(transPers);
	}

	@Override
	public List<Transaction> getAllTransactions() throws Exception {
		List<Transaction> list = new ArrayList<>();
		for (TransactionPersistanceModel transModel : repo.findAll()) {
			Transaction.TransactionBuilder trans = Transaction.builder();
			trans.setcreatedAt(transModel.getCreatedAt()).setDesc(transModel.getDescription())
					.setId(transModel.getTransactionId());
			for (TransactionEntryPersistenceModel ent : transModel.getEntries()) {
				TransactionEntry entry = TransactionEntry.builder().setId(ent.getEntryId())
						.setAccountId(ent.getAccountId()).setTransactionId(transModel.getTransactionId())
						.setCreatedAt(ent.getCreatedAt()).setAmount(ent.getAmount()).setDirection(ent.getDirection())
						.build();
				trans.addEntry(entry);
			}
			list.add(trans.build());
		}
		return list;
	}

	@Override
	public Transaction getTransactionWithId(UUID uuid) throws Exception {
		TransactionPersistanceModel transModel = repo.findByTransactionId(uuid);

		Transaction.TransactionBuilder trans = Transaction.builder();
		trans.setcreatedAt(transModel.getCreatedAt()).setDesc(transModel.getDescription())
				.setId(transModel.getTransactionId());
		for (TransactionEntryPersistenceModel ent : transModel.getEntries()) {
			TransactionEntry entry = TransactionEntry.builder().setId(ent.getEntryId()).setAccountId(ent.getAccountId())
					.setTransactionId(transModel.getTransactionId()).setCreatedAt(ent.getCreatedAt())
					.setAmount(ent.getAmount()).setDirection(ent.getDirection()).build();
			trans.addEntry(entry);
		}

		return trans.build();

	}

}
