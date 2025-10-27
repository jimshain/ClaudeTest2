package com.jshain.dda.product.message;

import java.math.BigDecimal;

public class DdaProductMo {
	public DdaProductKey ddaProductKey;

	private String description;
	private BigDecimal minimumOpeningDeposit;
	private BigDecimal minimumBalance;
	private BigDecimal overdraftLimit;
	private BigDecimal apy;

	public DdaProductKey getDdaProductKey() {
		return ddaProductKey;
	}

	public void setDdaProductKey(DdaProductKey ddaProductKey) {
		this.ddaProductKey = ddaProductKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
