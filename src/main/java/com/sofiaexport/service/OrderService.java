package com.sofiaexport.service;

import com.sofiaexport.commands.AddItemToOrderCommand;
import com.sofiaexport.exception.InsufficientQuantityException;
import com.sofiaexport.exception.OrderAlreadyCompletedException;
import com.sofiaexport.exception.OrderNotFoundException;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.User;
import com.sofiaexport.model.UserOrder;
import com.sofiaexport.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void checkoutOrder(Long orderId) {
        UserOrder order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new OrderAlreadyCompletedException(orderId);
        }

        Set<Long> outOfStockAutoPartsIds = getOutOfStockAutoPartsIds(order.getAutoPartsInOrder());
        if (!outOfStockAutoPartsIds.isEmpty()) {
            throw new InsufficientQuantityException(
                    "AutoParts with the following ids: " + outOfStockAutoPartsIds + " are out of stock");
        }

        autoPartService.decreaseStock(order.getAutoPartsInOrder());

        order.setStatus(OrderStatus.COMPLETED);
        order.setTimestamp(Instant.now().getEpochSecond()); // TODO instant?
        orderRepository.save(order);
    }

    private Set<Long> getOutOfStockAutoPartsIds(Set<AutoPart> autoPartsToPurchase) {
        return autoPartsToPurchase
                .stream()
                .filter(autoPart -> autoPart.getCountInStockItems() == 0)
                .map(AutoPart::getId)
                .collect(Collectors.toSet());
    }
}
