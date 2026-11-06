package com.ledger.ledgermicroservice;

import java.math.BigDecimal;
import java.util.List;

record TransactionEntryRecord(Long accountId, LedgerEntry.DebitCredit type, BigDecimal amount) {
}

record TransactionRecord(List<TransactionEntryRecord> entries, String desc) {
}
