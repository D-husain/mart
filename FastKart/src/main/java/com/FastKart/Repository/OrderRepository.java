package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.Orders;
import com.FastKart.entities.User;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	List<Orders> findByUser(User loggedInUser);

	List<Orders> findByUserId(Integer userId);

}
