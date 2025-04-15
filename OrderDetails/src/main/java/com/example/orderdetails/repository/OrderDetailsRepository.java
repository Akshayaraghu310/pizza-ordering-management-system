package com.example.orderdetails.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderdetails.entity.OrderDetailsEntity;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long>{
	List<OrderDetailsEntity> findByCustomerId(Long customerId);

	OrderDetailsEntity findByOrderId(Long orderId);
}

