CREATE TABLE dda_product (
    holding_company_id VARCHAR(50) NOT NULL,
    bank_id VARCHAR(50) NOT NULL,
    branch_id VARCHAR(50) NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    product_description VARCHAR(255),
    minimum_opening_deposit DECIMAL(15, 2),
    minimum_balance DECIMAL(15, 2),
    overdraft_limit DECIMAL(15, 2),
    apy DECIMAL(5, 4),
    insert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    delete_flag BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (holding_company_id, bank_id, branch_id, product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
