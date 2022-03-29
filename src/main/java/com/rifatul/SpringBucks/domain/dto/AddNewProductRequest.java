package com.rifatul.SpringBucks.domain.dto;

public record AddNewProductRequest (String name, Double price, int categoryId) {
}