package com.jshain.dda.product.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access class for DdaProductDo operations.
 * Provides methods to insert, select, and update DDA Product records.
 */
public class DdaProductDb {
    private static final String TABLE_NAME = "dda_product";

    private static final String INSERT_SQL =
        "INSERT INTO " + TABLE_NAME + " (holding_company_id, bank_id, branch_id, product_id) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id FROM " + TABLE_NAME;

    private static final String SELECT_BY_ID_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id FROM " + TABLE_NAME + " " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ?";

    private static final String UPDATE_SQL =
        "UPDATE " + TABLE_NAME + " SET holding_company_id = ?, bank_id = ?, branch_id = ?, product_id = ? " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ?";

    /**
     * Inserts a new DDA Product record into the database.
     *
     * @param connection the database connection
     * @param product the DdaProductDo object to insert
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public int insert(Connection connection, DdaProductDo product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            stmt.setString(1, product.getHoldingCompanyId());
            stmt.setString(2, product.getBankId());
            stmt.setString(3, product.getBranchId());
            stmt.setString(4, product.getProductId());
            return stmt.executeUpdate();
        }
    }

    /**
     * Selects all DDA Product records from the database.
     *
     * @param connection the database connection
     * @return a list of DdaProductDo objects
     * @throws SQLException if a database error occurs
     */
    public List<DdaProductDo> selectAll(Connection connection) throws SQLException {
        List<DdaProductDo> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DdaProductDo product = new DdaProductDo();
                product.setHoldingCompanyId(rs.getString("holding_company_id"));
                product.setBankId(rs.getString("bank_id"));
                product.setBranchId(rs.getString("branch_id"));
                product.setProductId(rs.getString("product_id"));
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Selects a specific DDA Product record by its composite key.
     *
     * @param connection the database connection
     * @param holdingCompanyId the holding company ID
     * @param bankId the bank ID
     * @param branchId the branch ID
     * @param productId the product ID
     * @return the DdaProductDo object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public DdaProductDo selectById(Connection connection, String holdingCompanyId,
                                   String bankId, String branchId, String productId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setString(1, holdingCompanyId);
            stmt.setString(2, bankId);
            stmt.setString(3, branchId);
            stmt.setString(4, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DdaProductDo product = new DdaProductDo();
                    product.setHoldingCompanyId(rs.getString("holding_company_id"));
                    product.setBankId(rs.getString("bank_id"));
                    product.setBranchId(rs.getString("branch_id"));
                    product.setProductId(rs.getString("product_id"));
                    return product;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing DDA Product record in the database.
     * This method uses the old values to identify the record and updates it with new values.
     *
     * @param connection the database connection
     * @param oldProduct the existing DdaProductDo object (used for WHERE clause)
     * @param newProduct the new DdaProductDo object with updated values
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public int update(Connection connection, DdaProductDo oldProduct, DdaProductDo newProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            // SET clause - new values
            stmt.setString(1, newProduct.getHoldingCompanyId());
            stmt.setString(2, newProduct.getBankId());
            stmt.setString(3, newProduct.getBranchId());
            stmt.setString(4, newProduct.getProductId());

            // WHERE clause - old values
            stmt.setString(5, oldProduct.getHoldingCompanyId());
            stmt.setString(6, oldProduct.getBankId());
            stmt.setString(7, oldProduct.getBranchId());
            stmt.setString(8, oldProduct.getProductId());

            return stmt.executeUpdate();
        }
    }

    /**
     * Updates a DDA Product record identified by its composite key.
     *
     * @param connection the database connection
     * @param holdingCompanyId the holding company ID (identifier)
     * @param bankId the bank ID (identifier)
     * @param branchId the branch ID (identifier)
     * @param productId the product ID (identifier)
     * @param updatedProduct the DdaProductDo object with new values
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public int updateById(Connection connection, String holdingCompanyId, String bankId,
                         String branchId, String productId, DdaProductDo updatedProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            // SET clause - new values
            stmt.setString(1, updatedProduct.getHoldingCompanyId());
            stmt.setString(2, updatedProduct.getBankId());
            stmt.setString(3, updatedProduct.getBranchId());
            stmt.setString(4, updatedProduct.getProductId());

            // WHERE clause - identifiers
            stmt.setString(5, holdingCompanyId);
            stmt.setString(6, bankId);
            stmt.setString(7, branchId);
            stmt.setString(8, productId);

            return stmt.executeUpdate();
        }
    }
}
