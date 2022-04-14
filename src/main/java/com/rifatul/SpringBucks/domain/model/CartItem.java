package com.rifatul.SpringBucks.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

public record CartItem (int id, int orderId, int productId, int quantity, Timestamp lastUpdated) {}