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
    private final String id;
    private final String userId;
    private final List<String> itemIds;
    private final Double sum;
    private final OrderStatus orderStatus;

    public static UserOrderResponse from(UserOrder order) {
        List<String> items = order.getAutoPartsInOrder()
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
