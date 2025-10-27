package com.jshain.dda.product.message;

import java.time.LocalDateTime;

public class DdaProductKey {
	private String holdingCompanyId;
	private String bankId;
	private String branchId;
	private String productId;
	private LocalDateTime effectiveDate;

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

	public LocalDateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
