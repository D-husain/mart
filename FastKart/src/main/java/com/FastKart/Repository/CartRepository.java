package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Cart;
import com.FastKart.entities.User;

public interface CartRepository extends CrudRepository<Cart, Integer> {


	List<Cart> findByUser(User user);
	
	// this method automatically count all the cart item according to login user
	
	int countByUser(User user);
	
}


