package io.vincent.learning.stack.concurrency.locks;

class Stock {

    private Integer quantity;

    public Stock(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
