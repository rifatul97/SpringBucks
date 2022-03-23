package com.rifatul.SpringBucks.domain.dto;

public record UserRegisterRequest(String firstname,
                                  String lastname,
                                  String email,
                                  String password) {
}