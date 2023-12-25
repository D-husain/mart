package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

public interface WishListRepository  extends CrudRepository<WishList, Integer >{

	// this method give all the product which add by user in wishList according to user login
	List<WishList> findByUser(User user);
	
	// this method is automatically count cart item according to user login
	int countByUser(User user);
	
	
}
