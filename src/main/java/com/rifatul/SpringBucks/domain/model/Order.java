package com.rifatul.SpringBucks.domain.model;

import java.sql.Timestamp;

public record Order(long orderId,
                    Timestamp dateAdded,
                    Timestamp lastUpdated,
                    OrderStatus orderStatus) {
}