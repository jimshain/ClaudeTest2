package com.jshain.dda.product.database;

import java.time.LocalDateTime;

/**
 * Represents a history record containing old and new versions of a DDA Product.
 */
public class DdaProductHistoryDo {
    private DdaProductDo oldProduct;
    private DdaProductDo newProduct;
    private LocalDateTime historyInsertDate;

    public DdaProductHistoryDo() {
    }

    public DdaProductHistoryDo(DdaProductDo oldProduct, DdaProductDo newProduct, LocalDateTime historyInsertDate) {
        this.oldProduct = oldProduct;
        this.newProduct = newProduct;
        this.historyInsertDate = historyInsertDate;
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
