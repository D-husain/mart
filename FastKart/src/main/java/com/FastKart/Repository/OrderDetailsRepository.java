package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
