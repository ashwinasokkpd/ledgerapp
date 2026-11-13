package com.ledger.ledgermicroservice.transaction.model;

import java.util.List;

public record TransactionRecord(List<TransactionEntryRecord> entries, String desc) {
}
