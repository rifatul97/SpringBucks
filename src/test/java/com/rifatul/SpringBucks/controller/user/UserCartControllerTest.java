package com.rifatul.SpringBucks.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rifatul.SpringBucks.domain.dto.CartDTO;
import com.rifatul.SpringBucks.domain.dto.OrderDTO;
import com.rifatul.SpringBucks.service.order.OrderService;
import com.rifatul.SpringBucks.service.order.UserCartItemsService;
import com.rifatul.SpringBucks.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.rifatul.SpringBucks.testutils.CONSTANTS.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserCartControllerTest {

    @Autowired
    private UserCartItemsService cartItemsService;
    @Autowired private UserService userService;
    @Autowired private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("item cannot be add to cart without user login")
    public void test1() throws Exception {
        mockMvc.perform(get(get_current_user_cart))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("after user login and adding item to cart and the cart will display the added item")
    public void test2() throws Exception {
        var token = mockMvc
                .perform(post(user_login).with(httpBasic(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD)))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(user_adds_item_to_cart)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAddItemToCartDTO(3, 3))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        var json = mockMvc.perform(MockMvcRequestBuilders
                        .get(get_current_user_cart)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        CartDTO.Response.Items items = new ObjectMapper().readValue(json, CartDTO.Response.Items.class);

        assertTrue(items.cartItems().size() == 1);
    }

    @Test
    @DisplayName("after changing a cart item amount, the overall cart items will display the updated amount")
    public void test3() throws Exception {
        var token = mockMvc
                .perform(post(user_login).with(httpBasic(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD)))
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(user_adds_item_to_cart)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAddItemToCartDTO(3, 3))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(user_updates_cart_item)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAddItemToCartDTO(3, 1))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        var json = mockMvc.perform(MockMvcRequestBuilders
                        .get(get_current_user_cart)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        CartDTO.Response.Items items = new ObjectMapper().readValue(json, CartDTO.Response.Items.class);

        assertTrue(items.cartItems().get(0).product().getId() == 3 && items.cartItems().get(0).quantity() == 1);
    }

    @Test
    @DisplayName("after remove a cart item, the overall cart items will display the correct change")
    public void test4() {

    }

    @Test
    @DisplayName("")
    public void test5() {

    }

}