ALTER TABLE ledger_accounts ADD CONSTRAINT chk_balance_last_at_must_both_be_null_or_with_value CHECK(
((balance IS NOT NULL)=(last_at IS NOT NULL))
)