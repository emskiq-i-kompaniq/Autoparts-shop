package com.sofiaexport.controller;

import com.sofiaexport.commands.AddItemToOrderCommand;
import com.sofiaexport.exception.UserNotFoundException;
import com.sofiaexport.model.UserOrder;
import com.sofiaexport.response.UserOrderResponse;
import com.sofiaexport.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "/v1/add-item")
    public void addItemToOrder(@RequestBody final AddItemToOrderCommand command) {
        orderService.addItemToOrder(command);
    }

    @GetMapping(path = "/v1/user/{userId}/order", produces = "application/json")
    public UserOrderResponse getPendingOrder(@PathVariable long userId) {
        UserOrder order = orderService.findPendingOrderForUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return UserOrderResponse.from(order);
    }
}
