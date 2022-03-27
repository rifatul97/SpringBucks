package com.rifatul.SpringBucks.testutils;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;
// given/when/then format - BDD style
// given - setup or precondition
// when - action
// then - verify the output
public enum TestUser implements GrantedAuthority {
    ROLE_CUSTOMER("test", "pass"),
    ROLE_ADMIN("rifat97@gmail.com", "pass"),
    ROLE_BARISTA("john1990@yahoo.com", "pass");

    public final String email;
    public final String password;

    TestUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getInfoAsJson() {
        Map<String, String> map = new HashMap<>();
        map.put("email", this.email);
        map.put("password", this.password);
        return new JSONObject(map).toString();
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}