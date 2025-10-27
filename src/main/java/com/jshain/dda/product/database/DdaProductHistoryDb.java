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
 * Database access class for DDA Product History operations.
 * This class stores old and new versions of DdaProductDo records in a history table.
 * Only insert and select operations are supported - no updates or deletes.
 */
public class DdaProductHistoryDb {
    private static final String TABLE_NAME = "dda_product_history";

    private static final String INSERT_SQL =
        "INSERT INTO " + TABLE_NAME + " (" +
        "holding_company_id, bank_id, branch_id, product_id, " +
        "old_product_description, old_minimum_opening_deposit, old_minimum_balance, " +
        "old_overdraft_limit, old_apy, old_insert_date, old_update_date, old_updated_by, " +
        "new_product_description, new_minimum_opening_deposit, new_minimum_balance, " +
        "new_overdraft_limit, new_apy, new_insert_date, new_update_date, new_updated_by, " +
        "history_insert_date) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, " +
        "old_product_description, old_minimum_opening_deposit, old_minimum_balance, " +
        "old_overdraft_limit, old_apy, old_insert_date, old_update_date, old_updated_by, " +
        "new_product_description, new_minimum_opening_deposit, new_minimum_balance, " +
        "new_overdraft_limit, new_apy, new_insert_date, new_update_date, new_updated_by, " +
        "history_insert_date FROM " + TABLE_NAME + " ORDER BY history_insert_date DESC";

    private static final String SELECT_BY_ID_SQL =
        "SELECT holding_company_id, bank_id, branch_id, product_id, " +
        "old_product_description, old_minimum_opening_deposit, old_minimum_balance, " +
        "old_overdraft_limit, old_apy, old_insert_date, old_update_date, old_updated_by, " +
        "new_product_description, new_minimum_opening_deposit, new_minimum_balance, " +
        "new_overdraft_limit, new_apy, new_insert_date, new_update_date, new_updated_by, " +
        "history_insert_date FROM " + TABLE_NAME + " " +
        "WHERE holding_company_id = ? AND bank_id = ? AND branch_id = ? AND product_id = ? " +
        "ORDER BY history_insert_date DESC";

    /**
     * Inserts a new history record containing both old and new versions of a DDA Product.
     * Automatically sets history_insert_date to the current timestamp.
     *
     * @param connection the database connection
     * @param oldProduct the old version of the DdaProductDo object
     * @param newProduct the new version of the DdaProductDo object
     * @return the number of rows affected
     * @throws SQLException if a database error occurs
     */
    public int insert(Connection connection, DdaProductDo oldProduct, DdaProductDo newProduct) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            LocalDateTime now = LocalDateTime.now();

            // Key attributes (from new product)
            stmt.setString(1, newProduct.getHoldingCompanyId());
            stmt.setString(2, newProduct.getBankId());
            stmt.setString(3, newProduct.getBranchId());
            stmt.setString(4, newProduct.getProductId());

            // Old product attributes
            stmt.setString(5, oldProduct.getProductDescription());
            stmt.setBigDecimal(6, oldProduct.getMinimumOpeningDeposit());
            stmt.setBigDecimal(7, oldProduct.getMinimumBalance());
            stmt.setBigDecimal(8, oldProduct.getOverdraftLimit());
            stmt.setBigDecimal(9, oldProduct.getApy());
            stmt.setTimestamp(10, oldProduct.getInsertDate() != null ? Timestamp.valueOf(oldProduct.getInsertDate()) : null);
            stmt.setTimestamp(11, oldProduct.getUpdateDate() != null ? Timestamp.valueOf(oldProduct.getUpdateDate()) : null);
            stmt.setString(12, oldProduct.getUpdatedBy());

            // New product attributes
            stmt.setString(13, newProduct.getProductDescription());
            stmt.setBigDecimal(14, newProduct.getMinimumOpeningDeposit());
            stmt.setBigDecimal(15, newProduct.getMinimumBalance());
            stmt.setBigDecimal(16, newProduct.getOverdraftLimit());
            stmt.setBigDecimal(17, newProduct.getApy());
            stmt.setTimestamp(18, newProduct.getInsertDate() != null ? Timestamp.valueOf(newProduct.getInsertDate()) : null);
            stmt.setTimestamp(19, newProduct.getUpdateDate() != null ? Timestamp.valueOf(newProduct.getUpdateDate()) : null);
            stmt.setString(20, newProduct.getUpdatedBy());

            // History insert date
            stmt.setTimestamp(21, Timestamp.valueOf(now));

            return stmt.executeUpdate();
        }
    }

    /**
     * Selects all DDA Product history records from the database.
     * Records are ordered by history_insert_date in descending order (most recent first).
     *
     * @param connection the database connection
     * @return a list of DdaProductHistoryDo objects
     * @throws SQLException if a database error occurs
     */
    public List<DdaProductHistoryDo> selectAll(Connection connection) throws SQLException {
        List<DdaProductHistoryDo> historyRecords = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historyRecords.add(extractHistoryRecord(rs));
            }
        }
        return historyRecords;
    }

    /**
     * Selects all history records for a specific DDA Product identified by its composite key.
     * Records are ordered by history_insert_date in descending order (most recent first).
     *
     * @param connection the database connection
     * @param holdingCompanyId the holding company ID
     * @param bankId the bank ID
     * @param branchId the branch ID
     * @param productId the product ID
     * @return a list of DdaProductHistoryDo objects for the specified product
     * @throws SQLException if a database error occurs
     */
    public List<DdaProductHistoryDo> selectById(Connection connection, String holdingCompanyId,
                                                     String bankId, String branchId, String productId) throws SQLException {
        List<DdaProductHistoryDo> historyRecords = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setString(1, holdingCompanyId);
            stmt.setString(2, bankId);
            stmt.setString(3, branchId);
            stmt.setString(4, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    historyRecords.add(extractHistoryRecord(rs));
                }
            }
        }
        return historyRecords;
    }

    /**
     * Helper method to extract a DdaProductHistoryDo from a ResultSet.
     *
     * @param rs the ResultSet to extract from
     * @return a DdaProductHistoryDo object
     * @throws SQLException if a database error occurs
     */
    private DdaProductHistoryDo extractHistoryRecord(ResultSet rs) throws SQLException {
        DdaProductHistoryDo record = new DdaProductHistoryDo();

        // Extract key attributes
        String holdingCompanyId = rs.getString("holding_company_id");
        String bankId = rs.getString("bank_id");
        String branchId = rs.getString("branch_id");
        String productId = rs.getString("product_id");

        // Create old product
        DdaProductDo oldProduct = new DdaProductDo(holdingCompanyId, bankId, branchId, productId);
        oldProduct.setProductDescription(rs.getString("old_product_description"));
        oldProduct.setMinimumOpeningDeposit(rs.getBigDecimal("old_minimum_opening_deposit"));
        oldProduct.setMinimumBalance(rs.getBigDecimal("old_minimum_balance"));
        oldProduct.setOverdraftLimit(rs.getBigDecimal("old_overdraft_limit"));
        oldProduct.setApy(rs.getBigDecimal("old_apy"));
        Timestamp oldInsertTimestamp = rs.getTimestamp("old_insert_date");
        oldProduct.setInsertDate(oldInsertTimestamp != null ? oldInsertTimestamp.toLocalDateTime() : null);
        Timestamp oldUpdateTimestamp = rs.getTimestamp("old_update_date");
        oldProduct.setUpdateDate(oldUpdateTimestamp != null ? oldUpdateTimestamp.toLocalDateTime() : null);
        oldProduct.setUpdatedBy(rs.getString("old_updated_by"));

        // Create new product
        DdaProductDo newProduct = new DdaProductDo(holdingCompanyId, bankId, branchId, productId);
        newProduct.setProductDescription(rs.getString("new_product_description"));
        newProduct.setMinimumOpeningDeposit(rs.getBigDecimal("new_minimum_opening_deposit"));
        newProduct.setMinimumBalance(rs.getBigDecimal("new_minimum_balance"));
        newProduct.setOverdraftLimit(rs.getBigDecimal("new_overdraft_limit"));
        newProduct.setApy(rs.getBigDecimal("new_apy"));
        Timestamp newInsertTimestamp = rs.getTimestamp("new_insert_date");
        newProduct.setInsertDate(newInsertTimestamp != null ? newInsertTimestamp.toLocalDateTime() : null);
        Timestamp newUpdateTimestamp = rs.getTimestamp("new_update_date");
        newProduct.setUpdateDate(newUpdateTimestamp != null ? newUpdateTimestamp.toLocalDateTime() : null);
        newProduct.setUpdatedBy(rs.getString("new_updated_by"));

        // Extract history insert date
        Timestamp historyInsertTimestamp = rs.getTimestamp("history_insert_date");
        LocalDateTime historyInsertDate = historyInsertTimestamp != null ? historyInsertTimestamp.toLocalDateTime() : null;

        record.setOldProduct(oldProduct);
        record.setNewProduct(newProduct);
        record.setHistoryInsertDate(historyInsertDate);

        return record;
    }
}
