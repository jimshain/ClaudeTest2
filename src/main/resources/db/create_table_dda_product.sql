CREATE TABLE dda_product (
    holding_company_id TEXT NOT NULL,
    bank_id TEXT NOT NULL,
    branch_id TEXT NOT NULL,
    product_id TEXT NOT NULL,
    product_description TEXT,
    minimum_opening_deposit NUMERIC,
    minimum_balance NUMERIC,
    overdraft_limit NUMERIC,
    apy NUMERIC,
    insert_date TEXT DEFAULT CURRENT_TIMESTAMP,
    update_date TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_by TEXT,
    delete_flag INTEGER DEFAULT 0,
    PRIMARY KEY (holding_company_id, bank_id, branch_id, product_id)
);
