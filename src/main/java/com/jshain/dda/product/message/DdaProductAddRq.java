package com.jshain.dda.product.message;

import com.jshain.message.MessageRq;

public class DdaProductAddRq extends MessageRq {

	private DdaProductMo ddaProduct;

	public DdaProductMo getDdaProduct() {
		return ddaProduct;
	}

	public void setDdaProduct(DdaProductMo ddaProduct) {
		this.ddaProduct = ddaProduct;
	}

}
