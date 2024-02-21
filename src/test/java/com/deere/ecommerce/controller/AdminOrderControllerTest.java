package com.deere.ecommerce.controller;

import com.deere.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminOrderController.class)
class AdminOrderControllerTest {

    @InjectMocks
    AdminOrderController adminOrderController;

    @MockBean
    OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOrdersHandler() throws Exception {
//        mockMvc.perform(get("/getAllOrders")).andExpect(status().isAccepted());
    }

    @Test
    void confirmedOrderHandler() {
    }

    @Test
    void shippedOrderHandler() {
    }

    @Test
    void deliveredOrderHandler() {
    }

    @Test
    void canceledOrderHandler() {
    }

    @Test
    void deleteOrderHandler() {
    }
}