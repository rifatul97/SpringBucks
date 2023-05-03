package com.rifatul.SpringBucks.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-containers")
class DashboardControllerTest {


    @Autowired private MockMvc mockMvc;

    private final String base_api_test = "/api/v1/dashboard";
    private final String get_all_users = base_api_test + "/users";

    @Test
    @DisplayName("accessing user/all api as admin role return access denied error")
    public void test0() throws Exception {
        mockMvc.perform(get(get_all_users))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

//    @Test
//    @DisplayName("accessing user/all api as barista role return access denied error")
//    public void test1() throws Exception {
//        mockMvc.perform(get(get_all_users).cookie(generateTemporaryAccessCookie(ROLE_BARISTA)))
//                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//    }

//    @Test
//    @DisplayName("accessing user/all api as admin role outputs status OK")
//    public void test2() throws Exception {
//        mockMvc.perform(get(get_all_users).cookie(generateTemporaryAccessCookie(ROLE_ADMIN)))
//                .andExpect(status().is(HttpStatus.OK.value()));
//    }

}