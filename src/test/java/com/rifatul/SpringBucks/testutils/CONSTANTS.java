package com.rifatul.SpringBucks.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CONSTANTS {

    public static final String base_api_test = "/api/v1/users";
    public static final String user_login = "/api/v1/authenticate";
    public static final String user_logout = base_api_test + "/logout";
    public static final String register_new_user = base_api_test + "/register";
    public static final String current_login_username = "/api/v1/user";

    public static final String get_current_user_cart = "/api/v1/users/cart";
    public static final String user_adds_item_to_cart = "/api/v1/users/cart/add";
    public static final String user_updates_cart_item = "/api/v1/users/cart/update";
    public static final String user_removes_cart_item = "/api/v1/users/cart/remove";

    public static final String get_categories = "/api/v1/categories";


    public static final String TEST_ADMIN_EMAIL = "rifat97@gmail.com";
    public static final String TEST_ADMIN_PASSWORD = "pass";
    public static final String TEST_CUSTOMER_EMAIL = "honnymoon@amazon.com";
    public static final String TEST_CUSTOMER_PASSWORD = "pass";
    public static final String TEST_BARISTA_EMAIL = "john1990@yahoo.com";
    public static final String TEST_BARISTA_PASSWORD = "pass";

    public static final String newUserWithEmail(String testEmail) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("firstname", "joe");
        map.put("lastname", "smith");
        map.put("email", testEmail);
        map.put("password", "pass");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(map);

        return requestJson;
    }

    public static final String userLoginDto(String email, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return new JSONObject(map).toString();
    }

    public static final String userAddItemToCartDTO(int productId, int amount) throws JsonProcessingException {
        Map<String, Integer> map = new HashMap<>();
        map.put("productId", productId);
        map.put("quantity", amount);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(map);

        return requestJson;
    }




}
