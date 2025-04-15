package com.example.customerdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customerdetails.entity.CustomerDetailsEntity;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Long>{
	Optional<CustomerDetailsEntity> findByEmail(String email);

}
