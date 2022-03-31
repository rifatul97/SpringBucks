package com.rifatul.SpringBucks.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data @AllArgsConstructor @NoArgsConstructor
public class CartItem implements Serializable {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private Timestamp lastUpdated;
}