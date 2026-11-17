package com.ledger.ledgermicroservice.adapter.outbound.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.LedgerTransactionEntryPersistenceModel;
import com.ledger.ledgermicroservice.adapter.outbound.persistance.entity.LedgerTransactionPersistanceModel;
import com.ledger.ledgermicroservice.adapter.outbound.persistance.repository.LedgerTransactionRepo;
import com.ledger.ledgermicroservice.application.ports.outbound.LedgerTransactionPersistancePort;
import com.ledger.ledgermicroservice.domain.model.LedgerTransaction;
import com.ledger.ledgermicroservice.domain.model.LedgerTransactionEntry;

@Component
public class LedgerTransactionPersistanceAdapter implements LedgerTransactionPersistancePort {
	private final LedgerTransactionRepo repo;

	public LedgerTransactionPersistanceAdapter(LedgerTransactionRepo repo) {
		this.repo = repo;
	}

	@Override
	public void save(LedgerTransaction trs) {
		LedgerTransactionPersistanceModel transPers = new LedgerTransactionPersistanceModel(trs.getDescription(),
				trs.getCreatedAt());
		transPers.setTransactionId(trs.getTransactionId());
		for (LedgerTransactionEntry ent : trs.getEntries()) {
			LedgerTransactionEntryPersistenceModel lEntr = new LedgerTransactionEntryPersistenceModel(
					ent.getAccountId(), ent.getDirection(), ent.getAmount(), ent.getCreatedAt());
			lEntr.setId(ent.getEntryId());
			transPers.addEntry(lEntr);
		}
		repo.save(transPers);
	}

	@Override
	public List<LedgerTransaction> getAllTransactions() throws Exception {
		List<LedgerTransaction> list = new ArrayList<>();
		for (LedgerTransactionPersistanceModel transModel : repo.findAll()) {
			LedgerTransaction.TransactionBuilder trans = LedgerTransaction.builder();
			trans.setcreatedAt(transModel.getCreatedAt()).setDesc(transModel.getDescription())
					.setId(transModel.getTransactionId());
			for (LedgerTransactionEntryPersistenceModel ent : transModel.getEntries()) {
				LedgerTransactionEntry entry = LedgerTransactionEntry.builder().setId(ent.getId())
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
	public LedgerTransaction getTransactionWithId(UUID uuid) throws Exception {
		LedgerTransactionPersistanceModel transModel = repo.findByTransactionId(uuid);

		LedgerTransaction.TransactionBuilder trans = LedgerTransaction.builder();
		trans.setcreatedAt(transModel.getCreatedAt()).setDesc(transModel.getDescription())
				.setId(transModel.getTransactionId());
		for (LedgerTransactionEntryPersistenceModel ent : transModel.getEntries()) {
			LedgerTransactionEntry entry = LedgerTransactionEntry.builder().setId(ent.getId())
					.setAccountId(ent.getAccountId()).setTransactionId(transModel.getTransactionId())
					.setCreatedAt(ent.getCreatedAt()).setAmount(ent.getAmount()).setDirection(ent.getDirection())
					.build();
			trans.addEntry(entry);
		}

		return trans.build();

	}

}
