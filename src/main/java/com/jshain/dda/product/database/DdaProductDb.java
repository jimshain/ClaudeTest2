package com.jshain.dda.product.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access class for DdaProductDo operations.
 * Provides methods to insert, select, and update DDA Product records.
 */
public class DdaProductDb {
    private static final String TABLE_NAME = "dda_product";

    private static final String INSERT_SQL =
        "INSERT INTO " + TABLE_NAME + " (holding_company_id, bank_id, branch_id, product_id, effective_date, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, overdraft_fee, apy, " +
        "insert_date, update_date, updated_by) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, effective_date, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, overdraft_fee, apy, " +
        "insert_date, update_date, updated_by FROM " + TABLE_NAME + " " +
        "WHERE (delete_flag = 0 OR delete_flag IS NULL)";

    private static final String SELECT_BY_ID_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, effective_date, " +
        "product_description, minimum_opening_deposit, minimum_balance, overdraft_limit, overdraft_fee, apy, " +
        "insert_date, update_date, updated_by FROM " + TABLE_NAME + " " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ? AND effective_date = ? " +
        "AND (delete_flag = 0 OR delete_flag IS NULL)";

    private static final String UPDATE_SQL =
        "UPDATE " + TABLE_NAME + " SET holding_company_id = ?, bank_id = ?, branch_id = ?, product_id = ?, effective_date = ?, " +
        "product_description = ?, minimum_opening_deposit = ?, minimum_balance = ?, overdraft_limit = ?, overdraft_fee = ?, " +
        "apy = ?, update_date = ?, updated_by = ? " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ? AND effective_date = ?";

    private static final String DELETE_SQL =
        "UPDATE " + TABLE_NAME + " SET delete_flag = 1, update_date = ?, updated_by = ? " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ? AND effective_date = ?";

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
            stmt.setTimestamp(5, product.getEffectiveDate() != null ? Timestamp.valueOf(product.getEffectiveDate()) : null);
            stmt.setString(6, product.getProductDescription());
            stmt.setInt(7, product.getMinimumOpeningDeposit());
            stmt.setInt(8, product.getMinimumBalance());
            stmt.setInt(9, product.getOverdraftLimit());
            stmt.setInt(10, product.getOverdraftFee());
            stmt.setBigDecimal(11, product.getApy());
            stmt.setTimestamp(12, Timestamp.valueOf(now));
            stmt.setTimestamp(13, Timestamp.valueOf(now));
            stmt.setString(14, product.getUpdatedBy());
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
                Timestamp effectiveTimestamp = rs.getTimestamp("effective_date");
                product.setEffectiveDate(effectiveTimestamp != null ? effectiveTimestamp.toLocalDateTime() : null);
                product.setProductDescription(rs.getString("product_description"));
                product.setMinimumOpeningDeposit(rs.getInt("minimum_opening_deposit"));
                product.setMinimumBalance(rs.getInt("minimum_balance"));
                product.setOverdraftLimit(rs.getInt("overdraft_limit"));
                product.setOverdraftFee(rs.getInt("overdraft_fee"));
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
                                   Integer bankId, Integer branchId, String productId, LocalDateTime effectiveDate) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setInt(1, holdingCompanyId);
            stmt.setInt(2, bankId);
            stmt.setInt(3, branchId);
            stmt.setString(4, productId);
            stmt.setTimestamp(5, effectiveDate != null ? Timestamp.valueOf(effectiveDate) : null);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DdaProductDo product = new DdaProductDo();
                    product.setHoldingCompanyId(rs.getInt("holding_company_id"));
                    product.setBankId(rs.getInt("bank_id"));
                    product.setBranchId(rs.getInt("branch_id"));
                    product.setProductId(rs.getString("product_id"));
                    Timestamp effectiveTimestamp = rs.getTimestamp("effective_date");
                    product.setEffectiveDate(effectiveTimestamp != null ? effectiveTimestamp.toLocalDateTime() : null);
                    product.setProductDescription(rs.getString("product_description"));
                    product.setMinimumOpeningDeposit(rs.getInt("minimum_opening_deposit"));
                    product.setMinimumBalance(rs.getInt("minimum_balance"));
                    product.setOverdraftLimit(rs.getInt("overdraft_limit"));
                    product.setOverdraftFee(rs.getInt("overdraft_fee"));
                    product.setApy(rs.getBigDecimal("apy"));
                    product.setInsertDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getLong("insert_date")), ZoneId.systemDefault()));
                    product.setUpdateDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getLong("update_date")), ZoneId.systemDefault()));
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
            stmt.setTimestamp(5, newProduct.getEffectiveDate() != null ? Timestamp.valueOf(newProduct.getEffectiveDate()) : null);
            stmt.setString(6, newProduct.getProductDescription());
            stmt.setInt(7, newProduct.getMinimumOpeningDeposit());
            stmt.setInt(8, newProduct.getMinimumBalance());
            stmt.setInt(9, newProduct.getOverdraftLimit());
            stmt.setInt(10, newProduct.getOverdraftFee());
            stmt.setBigDecimal(11, newProduct.getApy());
            stmt.setTimestamp(12, Timestamp.valueOf(now));
            stmt.setString(13, newProduct.getUpdatedBy());

            // WHERE clause - old values
            stmt.setInt(14, oldProduct.getHoldingCompanyId());
            stmt.setInt(15, oldProduct.getBankId());
            stmt.setInt(16, oldProduct.getBranchId());
            stmt.setString(17, oldProduct.getProductId());
            stmt.setTimestamp(18, oldProduct.getEffectiveDate() != null ? Timestamp.valueOf(oldProduct.getEffectiveDate()) : null);

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
                         Integer branchId, String productId, LocalDateTime effectiveDate, DdaProductDo updatedProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            // SET clause - new values
            stmt.setInt(1, updatedProduct.getHoldingCompanyId());
            stmt.setInt(2, updatedProduct.getBankId());
            stmt.setInt(3, updatedProduct.getBranchId());
            stmt.setString(4, updatedProduct.getProductId());
            stmt.setTimestamp(5, updatedProduct.getEffectiveDate() != null ? Timestamp.valueOf(updatedProduct.getEffectiveDate()) : null);
            stmt.setString(6, updatedProduct.getProductDescription());
            stmt.setInt(7, updatedProduct.getMinimumOpeningDeposit());
            stmt.setInt(8, updatedProduct.getMinimumBalance());
            stmt.setInt(9, updatedProduct.getOverdraftLimit());
            stmt.setInt(10, updatedProduct.getOverdraftFee());
            stmt.setBigDecimal(11, updatedProduct.getApy());
            stmt.setTimestamp(12, Timestamp.valueOf(now));
            stmt.setString(13, updatedProduct.getUpdatedBy());

            // WHERE clause - identifiers
            stmt.setInt(14, holdingCompanyId);
            stmt.setInt(15, bankId);
            stmt.setInt(16, branchId);
            stmt.setString(17, productId);
            stmt.setTimestamp(18, effectiveDate != null ? Timestamp.valueOf(effectiveDate) : null);

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
            stmt.setTimestamp(7, product.getEffectiveDate() != null ? Timestamp.valueOf(product.getEffectiveDate()) : null);

            return stmt.executeUpdate();
        }
    }
}
