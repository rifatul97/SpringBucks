package com.rifatul.SpringBucks.domain.model;

import java.sql.Timestamp;

public record User(int id,
                   String firstname, String lastname,
                   String email, String password,
                   Timestamp createdAt) {

}