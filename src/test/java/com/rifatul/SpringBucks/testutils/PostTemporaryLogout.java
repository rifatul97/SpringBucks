package com.rifatul.SpringBucks.testutils;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTemporaryLogout {

    private static final String LOGOUT_ENDPOINT = "/api/v1/users/logout";

    public static void performLogout(MockMvc mockMvc) throws Exception {
        mockMvc.perform(post(LOGOUT_ENDPOINT)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();
    }

}
