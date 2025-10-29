package com.jshain.dda.product.message;

import com.jshain.dda.product.database.DdaProductDo;
import com.jshain.message.MessageRs;

public class DdaProductInqRs extends MessageRs {
	private DdaProductDo ddaProduct;

	public DdaProductDo getDdaProduct() {
		return ddaProduct;
	}

	public void setDdaProduct(DdaProductDo ddaProduct) {
		this.ddaProduct = ddaProduct;
	}

} // Class end