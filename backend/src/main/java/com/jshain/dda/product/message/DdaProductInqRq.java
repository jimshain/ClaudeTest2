package com.jshain.dda.product.message;

import com.jshain.message.MessageRq;

public class DdaProductInqRq extends MessageRq {

	private DdaProductKey ddaProductKey;

	public DdaProductKey getDdaProductKey() {
		return ddaProductKey;
	}

	public void setDdaProductKey(DdaProductKey ddaProductKey) {
		this.ddaProductKey = ddaProductKey;
	}

} // Class end
