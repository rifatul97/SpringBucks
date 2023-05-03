package com.rifatul.SpringBucks.domain.model;

public enum OrderStatus {
    NEW, ONQUEUE, FULFILLING, COMPLETED, CANCELLED;

    public OrderStatus getNextStep() {
        for (int i = 0; i < OrderStatus.values().length - 1; i++) {
            if (OrderStatus.values()[i].equals(this)) {
                return OrderStatus.values()[i + 1];
            }
        }
        return null;
    }

}