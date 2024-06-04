package org.example;

import java.sql.Timestamp;

public class Sales {
    private int id;
    private int productId;
    private int quantity;
    private Timestamp saleDate;

    public Sales(int id, int productId, int quantity, Timestamp saleDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", saleDate=" + saleDate +
                '}';
    }
}
