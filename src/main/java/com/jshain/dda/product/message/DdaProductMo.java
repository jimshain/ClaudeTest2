package com.jshain.dda.product.message;

import java.math.BigDecimal;

public class DdaProductMo {
	public DdaProductKey ddaProductKey;

	private String description;
	private Integer minimumOpeningDeposit;
	private Integer minimumBalance;
	private Integer overdraftLimit;
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

	public Integer getMinimumOpeningDeposit() {
		return minimumOpeningDeposit;
	}

	public void setMinimumOpeningDeposit(Integer minimumOpeningDeposit) {
		this.minimumOpeningDeposit = minimumOpeningDeposit;
	}

	public Integer getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Integer minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public Integer getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(Integer overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	public BigDecimal getApy() {
		return apy;
	}

	public void setApy(BigDecimal apy) {
		this.apy = apy;
	}
}
