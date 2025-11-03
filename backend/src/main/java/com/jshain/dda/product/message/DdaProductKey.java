package com.jshain.dda.product.message;

import java.time.LocalDateTime;

public class DdaProductKey {
	private Integer holdingCompanyId;
	private Integer bankId;
	private Integer branchId;
	private String productId;
	private LocalDateTime effectiveDate;

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

	public LocalDateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
