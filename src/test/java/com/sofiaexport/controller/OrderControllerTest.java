package com.sofiaexport.controller;

import com.sofiaexport.commands.AddItemToOrderCommand;
import com.sofiaexport.commands.RemoveItemFromOrderCommand;
import com.sofiaexport.exception.UserNotFoundException;
import com.sofiaexport.model.User;
import com.sofiaexport.model.UserOrder;
import com.sofiaexport.service.OrderService;
import com.sofiaexport.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Test
    void addItemToOrder_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/add-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"userId\", \"itemId\":\"itemId\", \"quantity\":2}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).addItemToOrder(any(AddItemToOrderCommand.class));
    }

    @Test
    void removeItemFromOrder_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/remove-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"userId\", \"itemId\":\"itemId\", \"quantity\":2}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).removeItemFromOrder(any(RemoveItemFromOrderCommand.class));
    }

    @Test
    void getPendingOrder_WithExistingUserAndOrder_ShouldReturnUserOrderResponse() throws Exception {
        String userId = "existingUserId";
        UserOrder order = new UserOrder();
        when(userService.findUserById(userId)).thenReturn(new User("Edis", "edis@abv.bg", "password"));
        when(orderService.findPendingOrderForUser(userId)).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{userId}/order", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId));

        verify(userService, times(1)).findUserById(userId);
        verify(orderService, times(1)).findPendingOrderForUser(userId);
    }

    @Test
    void getPendingOrder_WithNonExistingUser_ShouldReturnNotFound() throws Exception {
        String userId = "nonExistingUserId";
        when(userService.findUserById(userId)).thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{userId}/order", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).findUserById(userId);
        verify(orderService, never()).findPendingOrderForUser(any());
    }

    @Test
    void getPendingOrder_WithExistingUserAndNoOrder_ShouldReturnNotFound() throws Exception {
        String userId = "existingUserId";
        when(userService.findUserById(userId)).thenReturn(new User("Edis", "edis@abv.bg", "password"));
        when(orderService.findPendingOrderForUser(userId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{userId}/order", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).findUserById(userId);
        verify(orderService, times(1)).findPendingOrderForUser(userId);
    }

    @Test
    void checkout_ShouldReturnOk() throws Exception {
        String orderId = "orderId";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order/{orderId}/checkout", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(orderService, times(1)).checkoutOrder(orderId);
    }

    @Test
    void getUserOrderHistory_WithExistingUser_ShouldReturnUserOrderResponses() throws Exception {
        String userId = "existingUserId";
        when(userService.findUserById(userId)).thenReturn(new User("Edis", "edis@abv.bg", "password"));
        when(orderService.findCompletedOrdersForUser(userId)).thenReturn(List.of(new UserOrder()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{userId}/orders", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));

        verify(userService, times(1)).findUserById(userId);
        verify(orderService, times(1)).findCompletedOrdersForUser(userId);
    }

    @Test
    void getUserOrderHistory_WithNonExistingUser_ShouldReturnNotFound() throws Exception {
        String userId = "nonExistingUserId";
        when(userService.findUserById(userId)).thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/{userId}/orders", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(userService, times(1)).findUserById(userId);
        verify(orderService, never()).findCompletedOrdersForUser(any());
    }
}
