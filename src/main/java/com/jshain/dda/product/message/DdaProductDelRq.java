package com.jshain.dda.product.message;

public class DdaProductDelRq {

	private String rquid;
	private DdaProductKey ddaProductKey;

	public String getRquid() {
		return rquid;
	}

	public void setRquid(String rquid) {
		this.rquid = rquid;
	}

	public DdaProductKey getDdaProductKey() {
		return ddaProductKey;
	}

	public void setDdaProductKey(DdaProductKey ddaProductKey) {
		this.ddaProductKey = ddaProductKey;
	}

} // Class end
