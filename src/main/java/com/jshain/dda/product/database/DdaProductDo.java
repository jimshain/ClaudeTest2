package com.jshain.dda.product.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Object representing a DDA Product entity.
 */
public class DdaProductDo {
    // Key attributes
    private Integer holdingCompanyId;
    private Integer bankId;
    private Integer branchId;
    private String productId;

    // Non-key attributes
    private String productDescription;
    private BigDecimal minimumOpeningDeposit;
    private BigDecimal minimumBalance;
    private BigDecimal overdraftLimit;
    private BigDecimal apy;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private String updatedBy;

    /**
     * Default constructor
     */
    public DdaProductDo() {
    }

    /**
     * Constructor with all fields
     */
    public DdaProductDo(Integer holdingCompanyId, Integer bankId, Integer branchId, String productId) {
        this.holdingCompanyId = holdingCompanyId;
        this.bankId = bankId;
        this.branchId = branchId;
        this.productId = productId;
    }

    // Getters and Setters
    public Integer getHoldingCompanyId() {
        return holdingCompanyId;
    }

    public void setHoldingCompanyId(Integer holdingCompanyId) {
        this.holdingCompanyId = holdingCompanyId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getMinimumOpeningDeposit() {
        return minimumOpeningDeposit;
    }

    public void setMinimumOpeningDeposit(BigDecimal minimumOpeningDeposit) {
        this.minimumOpeningDeposit = minimumOpeningDeposit;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getApy() {
        return apy;
    }

    public void setApy(BigDecimal apy) {
        this.apy = apy;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "DdaProductDo{" +
                "holdingCompanyId='" + holdingCompanyId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", productId='" + productId + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", minimumOpeningDeposit=" + minimumOpeningDeposit +
                ", minimumBalance=" + minimumBalance +
                ", overdraftLimit=" + overdraftLimit +
                ", apy=" + apy +
                ", insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DdaProductDo that = (DdaProductDo) o;

        if (holdingCompanyId != null ? !holdingCompanyId.equals(that.holdingCompanyId) : that.holdingCompanyId != null)
            return false;
        if (bankId != null ? !bankId.equals(that.bankId) : that.bankId != null) return false;
        if (branchId != null ? !branchId.equals(that.branchId) : that.branchId != null) return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;
    }

    @Override
    public int hashCode() {
        int result = holdingCompanyId != null ? holdingCompanyId.hashCode() : 0;
        result = 31 * result + (bankId != null ? bankId.hashCode() : 0);
        result = 31 * result + (branchId != null ? branchId.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
