CREATE TABLE dda_product (
    holding_company_id INTEGER NOT NULL,
    bank_id INTEGER NOT NULL,
    branch_id INTEGER NOT NULL,
    product_id TEXT NOT NULL,
    product_description TEXT,
    minimum_opening_deposit INTEGER,
    minimum_balance INTEGER,
    overdraft_limit INTEGER,
    apy NUMERIC,
    insert_date TEXT DEFAULT CURRENT_TIMESTAMP,
    update_date TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_by TEXT,
    delete_flag INTEGER DEFAULT 0,
    PRIMARY KEY (holding_company_id, bank_id, branch_id, product_id)
);
