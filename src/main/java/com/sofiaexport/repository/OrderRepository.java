package com.sofiaexport.repository;

import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, String> {
    List<UserOrder> findByUser_IdAndStatus(String userId, OrderStatus status);
}
