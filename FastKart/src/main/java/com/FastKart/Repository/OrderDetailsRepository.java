package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

	@Query("SELECT o FROM OrderDetails o WHERE o.order.id = :id") 
	  List<OrderDetails> findByOrderid(@Param("id") int orderId);

}
