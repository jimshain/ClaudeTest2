CREATE TABLE dda_product_history (
    history_id INTEGER PRIMARY KEY AUTOINCREMENT,
    holding_company_id INTEGER NOT NULL,
    bank_id INTEGER NOT NULL,
    branch_id INTEGER NOT NULL,
    product_id TEXT NOT NULL,
    product_description TEXT,
    minimum_opening_deposit INTEGER,
    minimum_balance INTEGER,
    overdraft_limit INTEGER,
    overdraft_fee INTEGER,
    apy NUMERIC,
    insert_date TEXT,
    update_date TEXT,
    updated_by TEXT,
    delete_flag INTEGER,
    operation_type TEXT NOT NULL,
    history_timestamp TEXT DEFAULT CURRENT_TIMESTAMP
);
