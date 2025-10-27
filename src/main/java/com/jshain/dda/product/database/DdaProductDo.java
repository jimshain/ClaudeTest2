package com.jshain.dda.product.database;

/**
 * Data Object representing a DDA Product entity.
 */
public class DdaProductDo {
    private String holdingCompanyId;
    private String bankId;
    private String branchId;
    private String productId;

    /**
     * Default constructor
     */
    public DdaProductDo() {
    }

    /**
     * Constructor with all fields
     */
    public DdaProductDo(String holdingCompanyId, String bankId, String branchId, String productId) {
        this.holdingCompanyId = holdingCompanyId;
        this.bankId = bankId;
        this.branchId = branchId;
        this.productId = productId;
    }

    // Getters and Setters
    public String getHoldingCompanyId() {
        return holdingCompanyId;
    }

    public void setHoldingCompanyId(String holdingCompanyId) {
        this.holdingCompanyId = holdingCompanyId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "DdaProductDo{" +
                "holdingCompanyId='" + holdingCompanyId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", productId='" + productId + '\'' +
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
