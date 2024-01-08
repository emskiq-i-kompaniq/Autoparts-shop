package com.sofiaexport.response;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.UserOrder;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserOrderResponse {
    private final Long id;
    private final Long userId;
    private final List<Long> itemIds;
    private final Double sum;
    private final OrderStatus orderStatus;

    public static UserOrderResponse from(UserOrder order) {
        List<Long> items = order.getAutoPartsInOrder()
                .stream()
                .map(AutoPart::getId)
                .toList();

        return UserOrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .sum(order.getSum())
                .itemIds(items)
                .orderStatus(order.getStatus())
                .build();
    }
}
