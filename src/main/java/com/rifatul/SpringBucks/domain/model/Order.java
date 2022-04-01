package com.rifatul.SpringBucks.domain.model;

import java.sql.Timestamp;

public record Order(long id,
                    Timestamp dateAdded,
                    Timestamp lastUpdated,
                    OrderStatus orderStatus) {
}