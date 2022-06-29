package com.hervmo.manager.utils;

public enum OrderType {
    CART("CART"),
    INVOICE("INVOICE");
    private final String orderType;

    OrderType(String ordertype) {
        this.orderType = ordertype;
    }

    @Override
    public String toString() {
        return orderType;
    }
}
