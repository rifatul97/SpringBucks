package com.rifatul.SpringBucks.controller.user;

import com.rifatul.SpringBucks.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.rifatul.SpringBucks.testutils.CONSTANTS.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private UserService userService;

    @Test
    @DisplayName("login fails with incorrect email or password and will return status 403 error")
    public void test1() throws Exception {
        mockMvc.perform(post(user_login).with(csrf())
                        .content(userLoginDto("inccorrect", "pass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("login works with correct user information and returns status 200 OK")
    public void test2() throws Exception {
        mockMvc.perform(post(user_login)
                        .with(httpBasic(TEST_ADMIN_EMAIL,TEST_ADMIN_PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("after user login returns jwt token")
    public void test3() throws Exception {
        var response = mockMvc.perform(post(user_login).with(httpBasic("rifat97@gmail.com","pass"))).andReturn().getResponse();

        assertTrue(response.getContentAsString().replace("\"", "").length() > 0);
    }

    @Test
    @DisplayName("after login, it will display the correct current user email")
    public void test4() throws Exception {
        var token = mockMvc
                .perform(post(user_login).with(httpBasic("rifat97@gmail.com","pass")))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/authenticate/get")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"rifat97@gmail.com\"}"));
    }
}