ALTER TABLE ledger_accounts ADD CONSTRAINT chk_balance_last_at_must_both_be_null_or_with_value CHECK(
((balance IS NOT NULL)=(last_at IS NOT NULL))
)
ALTER TABLE ledger_accounts ADD CONSTRAINT owner_id_must_not_be_null_for_all_accounts_except_some CHECK(
((owner_id IS NOT NULL) OR (owner_id IS NULL AND account_name IN ('bank_holding')) )
)

