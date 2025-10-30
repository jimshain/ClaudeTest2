package com.jshain.dda.product.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access class for DdaProductDo operations.
 * Provides methods to insert, select, and update DDA Product records.
 */
public class DdaProductDb {
    private static final String TABLE_NAME = "dda_product";

    private static final String INSERT_SQL =
        "INSERT INTO " + TABLE_NAME + " (holding_company_id, bank_id, branch_id, product_id, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, apy, " +
        "insert_date, update_date, updated_by) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, apy, " +
        "insert_date, update_date, updated_by FROM " + TABLE_NAME + " " +
        "WHERE (delete_flag = 0 OR delete_flag IS NULL)";

    private static final String SELECT_BY_ID_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, apy, " +
        "insert_date, update_date, updated_by FROM " + TABLE_NAME + " " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ? " +
        "AND (delete_flag = 0 OR delete_flag IS NULL)";

    private static final String UPDATE_SQL =
        "UPDATE " + TABLE_NAME + " SET holding_company_id = ?, bank_id = ?, branch_id = ?, product_id = ?, " +
        "product_description = ?, minimum_opening_deposit = ?, minimum_balance = ?, overdraft_limit = ?, " +
        "apy = ?, update_date = ?, updated_by = ? " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ?";

    private static final String DELETE_SQL =
        "UPDATE " + TABLE_NAME + " SET delete_flag = 1, update_date = ?, updated_by = ? " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ?";

    /**
     * Inserts a new DDA Product record into the database.
     * Automatically sets both insert_date and update_date to the current timestamp.
     *
     * @param connection the database connection
     * @param product the DdaProductDo object to insert
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public static int insert(Connection connection, DdaProductDo product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            stmt.setInt(1, product.getHoldingCompanyId());
            stmt.setInt(2, product.getBankId());
            stmt.setInt(3, product.getBranchId());
            stmt.setString(4, product.getProductId());
            stmt.setString(5, product.getProductDescription());
            stmt.setBigDecimal(6, product.getMinimumOpeningDeposit());
            stmt.setBigDecimal(7, product.getMinimumBalance());
            stmt.setBigDecimal(8, product.getOverdraftLimit());
            stmt.setBigDecimal(9, product.getApy());
            stmt.setTimestamp(10, Timestamp.valueOf(now));
            stmt.setTimestamp(11, Timestamp.valueOf(now));
            stmt.setString(12, product.getUpdatedBy());
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
    public static List<DdaProductDo> selectAll(Connection connection) throws SQLException {
        List<DdaProductDo> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DdaProductDo product = new DdaProductDo();
                product.setHoldingCompanyId(rs.getInt("holding_company_id"));
                product.setBankId(rs.getInt("bank_id"));
                product.setBranchId(rs.getInt("branch_id"));
                product.setProductId(rs.getString("product_id"));
                product.setProductDescription(rs.getString("product_description"));
                product.setMinimumOpeningDeposit(rs.getBigDecimal("minimum_opening_deposit"));
                product.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                product.setOverdraftLimit(rs.getBigDecimal("overdraft_limit"));
                product.setApy(rs.getBigDecimal("apy"));
                Timestamp insertTimestamp = rs.getTimestamp("insert_date");
                product.setInsertDate(insertTimestamp != null ? insertTimestamp.toLocalDateTime() : null);
                Timestamp updateTimestamp = rs.getTimestamp("update_date");
                product.setUpdateDate(updateTimestamp != null ? updateTimestamp.toLocalDateTime() : null);
                product.setUpdatedBy(rs.getString("updated_by"));
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
    public static DdaProductDo selectById(Connection connection, Integer holdingCompanyId,
                                   Integer bankId, Integer branchId, String productId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setInt(1, holdingCompanyId);
            stmt.setInt(2, bankId);
            stmt.setInt(3, branchId);
            stmt.setString(4, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DdaProductDo product = new DdaProductDo();
                    product.setHoldingCompanyId(rs.getInt("holding_company_id"));
                    product.setBankId(rs.getInt("bank_id"));
                    product.setBranchId(rs.getInt("branch_id"));
                    product.setProductId(rs.getString("product_id"));
                    product.setProductDescription(rs.getString("product_description"));
                    product.setMinimumOpeningDeposit(rs.getBigDecimal("minimum_opening_deposit"));
                    product.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                    product.setOverdraftLimit(rs.getBigDecimal("overdraft_limit"));
                    product.setApy(rs.getBigDecimal("apy"));
                    Timestamp insertTimestamp = rs.getTimestamp("insert_date");
                    product.setInsertDate(insertTimestamp != null ? insertTimestamp.toLocalDateTime() : null);
                    Timestamp updateTimestamp = rs.getTimestamp("update_date");
                    product.setUpdateDate(updateTimestamp != null ? updateTimestamp.toLocalDateTime() : null);
                    product.setUpdatedBy(rs.getString("updated_by"));
                    return product;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing DDA Product record in the database.
     * This method uses the old values to identify the record and updates it with new values.
     * Automatically sets update_date to the current timestamp; insert_date is never modified.
     *
     * @param connection the database connection
     * @param oldProduct the existing DdaProductDo object (used for WHERE clause)
     * @param newProduct the new DdaProductDo object with updated values
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public static int update(Connection connection, DdaProductDo oldProduct, DdaProductDo newProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            // SET clause - new values
            stmt.setInt(1, newProduct.getHoldingCompanyId());
            stmt.setInt(2, newProduct.getBankId());
            stmt.setInt(3, newProduct.getBranchId());
            stmt.setString(4, newProduct.getProductId());
            stmt.setString(5, newProduct.getProductDescription());
            stmt.setBigDecimal(6, newProduct.getMinimumOpeningDeposit());
            stmt.setBigDecimal(7, newProduct.getMinimumBalance());
            stmt.setBigDecimal(8, newProduct.getOverdraftLimit());
            stmt.setBigDecimal(9, newProduct.getApy());
            stmt.setTimestamp(10, Timestamp.valueOf(now));
            stmt.setString(11, newProduct.getUpdatedBy());

            // WHERE clause - old values
            stmt.setInt(12, oldProduct.getHoldingCompanyId());
            stmt.setInt(13, oldProduct.getBankId());
            stmt.setInt(14, oldProduct.getBranchId());
            stmt.setString(15, oldProduct.getProductId());

            return stmt.executeUpdate();
        }
    }

    /**
     * Updates a DDA Product record identified by its composite key.
     * Automatically sets update_date to the current timestamp; insert_date is never modified.
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
    public static int updateById(Connection connection, Integer holdingCompanyId, Integer bankId,
                         Integer branchId, String productId, DdaProductDo updatedProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            // SET clause - new values
            stmt.setInt(1, updatedProduct.getHoldingCompanyId());
            stmt.setInt(2, updatedProduct.getBankId());
            stmt.setInt(3, updatedProduct.getBranchId());
            stmt.setString(4, updatedProduct.getProductId());
            stmt.setString(5, updatedProduct.getProductDescription());
            stmt.setBigDecimal(6, updatedProduct.getMinimumOpeningDeposit());
            stmt.setBigDecimal(7, updatedProduct.getMinimumBalance());
            stmt.setBigDecimal(8, updatedProduct.getOverdraftLimit());
            stmt.setBigDecimal(9, updatedProduct.getApy());
            stmt.setTimestamp(10, Timestamp.valueOf(now));
            stmt.setString(11, updatedProduct.getUpdatedBy());

            // WHERE clause - identifiers
            stmt.setInt(12, holdingCompanyId);
            stmt.setInt(13, bankId);
            stmt.setInt(14, branchId);
            stmt.setString(15, productId);

            return stmt.executeUpdate();
        }
    }

    /**
     * Soft deletes a DDA Product record by setting the delete_flag to true.
     * Automatically sets update_date to the current timestamp.
     *
     * @param connection the database connection
     * @param product the DdaProductDo object to delete
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public static int delete(Connection connection, DdaProductDo product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            // SET clause
            stmt.setTimestamp(1, Timestamp.valueOf(now));
            stmt.setString(2, product.getUpdatedBy());

            // WHERE clause - composite key
            stmt.setInt(3, product.getHoldingCompanyId());
            stmt.setInt(4, product.getBankId());
            stmt.setInt(5, product.getBranchId());
            stmt.setString(6, product.getProductId());

            return stmt.executeUpdate();
        }
    }
}
