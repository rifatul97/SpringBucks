package com.rifatul.SpringBucks.controller;

import com.rifatul.SpringBucks.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.rifatul.SpringBucks.testutils.PostTemporaryLogin.*;
import static com.rifatul.SpringBucks.testutils.TestUser.ROLE_ADMIN;
import static com.rifatul.SpringBucks.testutils.TestUser.ROLE_BARISTA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test-containers")
//@DataJpaTest
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserService userService;

    private final String base_api_test = "/api/v1/users";
    private final String user_login = base_api_test + "/login";
    private final String user_logout = base_api_test + "/logout";
    private final String register_new_user = base_api_test + "/register";
    private final String current_login_username = base_api_test + "/current";

    @Test
    @DisplayName("if new user registers, the database correctly holds the new user data")
    public void test3() throws Exception {
        String testEmail = "jsmith0907@gmail.com";
        Map<String, String> map = new HashMap<>();
        map.put("firstname", "joe");
        map.put("lastname", "smith");
        map.put("email", testEmail);
        map.put("password", "pass");

        mockMvc.perform(post("/api/v1/users/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(map).toString()))
                .andExpect(status().is(200));

        System.out.println(userService.findAllUsers().toString());

        assertTrue(userService.findUserByEmail(testEmail).isPresent());
    }

    @Test
    @DisplayName("login fails with incorrect email or password and will return status 403 error")
    public void test4() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("email", "jsmith900@gmail.com");
        map.put("password", "passed");

        mockMvc.perform(post(user_login).with(csrf())
                        .content(new JSONObject(map).toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("login works with correct user information and returns status 200 OK")
    public void test5a() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("email", "rifat97@gmail.com");
        map.put("password", "pass");

        mockMvc.perform(post(user_login).with(csrf())
                .content(new JSONObject(map).toString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("displays correct current login user email")
    public void test5b() throws Exception {
        mockMvc.perform(get(current_login_username))
                .andExpect(status().isOk())
                .andExpect(content().string("rifat97@gmail.com"));
    }

    @Test
    @DisplayName("if user logout and return status 200 OK")
    public void test5c() throws Exception {
        mockMvc.perform(post(user_logout))
                .andExpect(status().isOk());
    }

}