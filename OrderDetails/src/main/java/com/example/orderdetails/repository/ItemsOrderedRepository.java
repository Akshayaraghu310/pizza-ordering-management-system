package com.example.orderdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderdetails.entity.ItemsOrderedEntity;

@Repository
public interface ItemsOrderedRepository extends JpaRepository<ItemsOrderedEntity, Long>{
	public ItemsOrderedEntity getById(Long id);

}
