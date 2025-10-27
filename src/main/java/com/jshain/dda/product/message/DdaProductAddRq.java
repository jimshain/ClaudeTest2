package com.jshain.dda.product.message;

public class DdaProductAddRq {

	private String rquid;
	private DdaProductMo ddaProduct;
	public String getRquid() {
		return rquid;
	}
	public void setRquid(String rquid) {
		this.rquid = rquid;
	}
	public DdaProductMo getDdaProduct() {
		return ddaProduct;
	}
	public void setDdaProduct(DdaProductMo ddaProduct) {
		this.ddaProduct = ddaProduct;
	}
	
}
