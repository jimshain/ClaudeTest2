package com.jshain.dda.product.database;

import java.time.LocalDateTime;

/**
 * Represents a history record containing old and new versions of a DDA Product.
 */
public class DdaProductHistoryDo {
	private String rquid;
    private DdaProductDo oldProduct;
    private DdaProductDo newProduct;
    private LocalDateTime historyInsertDate;

    public DdaProductHistoryDo() {
    }

    public DdaProductHistoryDo(String rquid, DdaProductDo oldProduct, DdaProductDo newProduct, LocalDateTime historyInsertDate) {
    	this.rquid = rquid;
        this.oldProduct = oldProduct;
        this.newProduct = newProduct;
        this.historyInsertDate = historyInsertDate;
    }
    
    public String getRquid() {
		return rquid;
	}

	public void setRquid(String rquid) {
		this.rquid = rquid;
	}

	public DdaProductDo getOldProduct() {
        return oldProduct;
    }

    public void setOldProduct(DdaProductDo oldProduct) {
        this.oldProduct = oldProduct;
    }

    public DdaProductDo getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(DdaProductDo newProduct) {
        this.newProduct = newProduct;
    }

    public LocalDateTime getHistoryInsertDate() {
        return historyInsertDate;
    }

    public void setHistoryInsertDate(LocalDateTime historyInsertDate) {
        this.historyInsertDate = historyInsertDate;
    }

    @Override
    public String toString() {
        return "DdaProductHistoryDo{" +
                "oldProduct=" + oldProduct +
                ", newProduct=" + newProduct +
                ", historyInsertDate=" + historyInsertDate +
                '}';
    }
}
