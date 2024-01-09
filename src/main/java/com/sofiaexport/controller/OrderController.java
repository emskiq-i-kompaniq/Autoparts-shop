package com.sofiaexport.controller;

import com.sofiaexport.commands.AddItemToOrderCommand;
import com.sofiaexport.commands.RemoveItemFromOrderCommand;
import com.sofiaexport.exception.OrderNotFoundException;
import com.sofiaexport.model.UserOrder;
import com.sofiaexport.response.UserOrderResponse;
import com.sofiaexport.service.OrderService;
import com.sofiaexport.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Tag(name = "Orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping(path = "/v1/add-item")
    public void addItemToOrder(@RequestBody final AddItemToOrderCommand command) {
        orderService.addItemToOrder(command);
    }

    @PostMapping(path = "/v1/remove-item")
    public void removeItemFromOrder(@RequestBody final RemoveItemFromOrderCommand command) {
        orderService.removeItemFromOrder(command);
    }

    @GetMapping(path = "/v1/user/{userId}/order", produces = "application/json")
    public UserOrderResponse getPendingOrder(@PathVariable String userId) {
        userService.findUserById(userId); // throws if user not found
        UserOrder order = orderService.findPendingOrderForUser(userId).orElseThrow(() -> new OrderNotFoundException(userId));
        return UserOrderResponse.from(order);
    }

    @PutMapping(path = "/v1/order/{orderId}/checkout")
    public void checkout(@PathVariable String orderId) {
        orderService.checkoutOrder(orderId);
    }
}
