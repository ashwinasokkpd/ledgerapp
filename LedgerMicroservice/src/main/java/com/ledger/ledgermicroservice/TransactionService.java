package com.ledger.ledgermicroservice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ledger.ledgermicroservice.TransactionRepo.TransactionRepoInterface;
import com.ledger.ledgermicroservice.domain.model.Account;
import com.ledger.ledgermicroservice.domain.model.Transaction;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {
	private final TransactionRepo transRepo;
	private final AccountRepo accountRepo;

	public TransactionService(TransactionRepo transRepo, AccountRepo accountRepo) {
		this.transRepo = transRepo;
		this.accountRepo = accountRepo;
	}

	private boolean isValid(TransactionRecord rec) {
		BigDecimal runningBalance = BigDecimal.ZERO;
		boolean hasAtLeastOneNormalDebitAccount = false;
		boolean hasAtLeastOneNormalCreditAccount = false;
		System.out.println(rec.entries().size());
		for (TransactionEntryRecord entr : rec.entries()) {
			try {
				Account acc = accountRepo.findById(entr.accountId()).orElseThrow(() -> new Exception("Error"));
				if (acc.getNormalType() == Account.NormalType.NORMAL_DEBIT_ACCOUNT) {
					runningBalance = entr.type() == LedgerEntry.DebitCredit.DEBIT ? runningBalance.add(entr.amount())
							: runningBalance.subtract(entr.amount());
					System.out.println(runningBalance);
					hasAtLeastOneNormalDebitAccount = true;
				} else {
					runningBalance = entr.type() == LedgerEntry.DebitCredit.CREDIT
							? runningBalance.subtract(entr.amount())
							: runningBalance.add(entr.amount());
					System.out.println(runningBalance);
					hasAtLeastOneNormalCreditAccount = true;
				}

			} catch (Exception ex) {
				return false;
			}

		}
		if (runningBalance.compareTo(BigDecimal.ZERO) != 0) {
			System.out.println(runningBalance);
			return false;
		}
		return hasAtLeastOneNormalCreditAccount && hasAtLeastOneNormalDebitAccount;
	}

	@Transactional
	public void addTransaction(TransactionRecord rec) {
		if (isValid(rec)) {
			Transaction trans = new Transaction(rec.desc(), LocalDateTime.now());
			for (TransactionEntryRecord entr : rec.entries()) {
				Account acc = accountRepo.findById(entr.accountId()).get();
				trans.addEntry(new LedgerEntry(acc, entr.type(), trans, entr.amount()));
			}
			transRepo.save(trans);

		}
	}

	public TransactionRepoInterface getTransactionWithId(Long id) {
		return transRepo.findProjectedById(id, TransactionRepoInterface.class);

	}

	public List<TransactionRepoInterface> getAllTransactions() {
		return transRepo.findProjectedBy();

	}

}
