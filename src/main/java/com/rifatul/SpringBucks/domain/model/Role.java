package com.rifatul.SpringBucks.domain.model;

import org.springframework.security.core.GrantedAuthority;

public record Role(int id, String name, String description) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
