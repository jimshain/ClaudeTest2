package com.jshain.dda.product.message;

import com.jshain.message.MessageRs;
import java.util.ArrayList;
import java.util.List;

public class DdaProductInqRs extends MessageRs {
	private DdaProductMo ddaProduct;
	private List<DdaProductMo> ddaProducts;

	public DdaProductMo getDdaProduct() {
		return ddaProduct;
	}

	public void setDdaProduct(DdaProductMo ddaProduct) {
		this.ddaProduct = ddaProduct;
	}

	public List<DdaProductMo> getDdaProducts() {
		return ddaProducts;
	}

	public void setDdaProducts(List<DdaProductMo> ddaProducts) {
		this.ddaProducts = ddaProducts;
	}

} // Class end