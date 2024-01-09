package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
