package com.ledger.ledgermicroservice.domain.model;

import java.util.List;

public record LedgerTransactionRecord(List<LedgerTransactionEntryRecord> entries, String desc) {
}
