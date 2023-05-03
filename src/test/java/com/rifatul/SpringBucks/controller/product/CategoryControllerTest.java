package com.rifatul.SpringBucks.controller.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.rifatul.SpringBucks.testutils.CONSTANTS.get_categories;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("get category by id outputs status OK")
    public void t1() throws Exception {
        mockMvc.perform(get(get_categories + "?id=" + 3)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get category using not existing category_id outputs status NOT FOUND")
    public void t12() throws Exception {
        mockMvc.perform(get(get_categories + "?id=" + 30)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("get categories outputs status OK")
    public void tnci1() throws Exception {
        mockMvc.perform(get(get_categories)
                .with(csrf()))
                .andExpect(status().isOk());
    }

}