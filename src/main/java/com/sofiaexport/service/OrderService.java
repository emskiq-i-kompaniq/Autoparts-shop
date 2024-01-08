package com.sofiaexport.service;

import com.sofiaexport.commands.AddItemToOrderCommand;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.User;
import com.sofiaexport.model.UserOrder;
import com.sofiaexport.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final AutoPartService autoPartService;

    public void addItemToOrder(AddItemToOrderCommand command) {
        AutoPart autoPartToAdd = autoPartService.findAutoPartById(command.getAutoPartId());
        User user = userService.findUserById(command.getUserId());

        Optional<UserOrder> existingOrder = findPendingOrderForUser(command.getUserId());
        if (existingOrder.isEmpty()) {
            UserOrder order = new UserOrder();
            order.setUser(user);
            order.setStatus(OrderStatus.PENDING);
            order.setSum(autoPartToAdd.getPrice());
            order.setAutoPartsInOrder(Set.of(autoPartToAdd));
            orderRepository.save(order);
            return;
        }

        UserOrder order = existingOrder.get();
        order.addAutoPart(autoPartToAdd);
        orderRepository.save(order);
    }

    public Optional<UserOrder> findPendingOrderForUser(Long userId) {
        List<UserOrder> pendingOrders = orderRepository.findByUser_IdAndStatus(userId, OrderStatus.PENDING);

        return pendingOrders.isEmpty()
                ? Optional.empty()
                : Optional.of(pendingOrders.get(0));
    }
}
