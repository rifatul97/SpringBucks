package com.rifatul.SpringBucks.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("basic get request test that returns hello world.")
    void home() throws Exception {
        this.mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("basic post body request")
    void basic_post_body_request() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("firstname", "rifat");
        map.put("lastname", "karim");
        System.out.println(new JSONObject(map).toString());

        MvcResult result = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(map).toString())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        assert(result.getResponse().getContentAsString()).equals("rifat karim");
    }

}