package com.sofiaexport.repository;

import com.sofiaexport.model.OrderStatus;
import com.sofiaexport.model.UserOrder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, String> {
    List<UserOrder> findByUser_IdAndStatus(String userId, OrderStatus status);

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Override
    <S extends UserOrder> S save(S entity);
}
