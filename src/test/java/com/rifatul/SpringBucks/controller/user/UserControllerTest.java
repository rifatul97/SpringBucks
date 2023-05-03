package com.rifatul.SpringBucks.controller.user;

import com.rifatul.SpringBucks.service.user.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static com.rifatul.SpringBucks.testutils.CONSTANTS.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserService userService;

    @Test
    @DisplayName("if new user registers, the database correctly holds the new user data")
    public void test1() throws Exception {
        String newUserEmail = "jsmith2604@gmail.com";

        mockMvc.perform(post("/api/v1/user/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserWithEmail(newUserEmail)))
                .andExpect(status().is(201));

        System.out.println(userService.findAllUsers().toString());

        assertTrue(userService.findUserByEmail(newUserEmail).isPresent());
    }


    @Test
    @DisplayName("if user register with existing email,the system displays error")
    public void test5() {

    }

    @Test
    @DisplayName("if user place order with empty cart, it will display error")
    public void test6() {

    }

}