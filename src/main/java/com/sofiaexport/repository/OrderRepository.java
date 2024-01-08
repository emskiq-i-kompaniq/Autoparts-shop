package com.sofiaexport.repository;

import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findByUser_IdAndStatus(Long userId, OrderStatus status);
}
