package com.jshain.dda.product.message;

import com.jshain.message.MessageRs;

public class DdaProductInqRs extends MessageRs {
	private DdaProductMo ddaProduct;

	public DdaProductMo getDdaProduct() {
		return ddaProduct;
	}

	public void setDdaProduct(DdaProductMo ddaProduct) {
		this.ddaProduct = ddaProduct;
	}

} // Class end