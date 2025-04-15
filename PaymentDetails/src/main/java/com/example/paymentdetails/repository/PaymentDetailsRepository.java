package com.example.paymentdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentdetails.entity.PaymentDetailsEntity;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetailsEntity, Long> {

}
