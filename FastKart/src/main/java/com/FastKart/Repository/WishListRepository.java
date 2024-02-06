package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

public interface WishListRepository  extends CrudRepository<WishList, Integer >{

	// this method give all the product which add by user in wishList according to user login
	List<WishList> findByUser(User user);
	
	// this method is automatically count cart item according to user login
	int countByUser(User user);

	@Query("SELECT w FROM WishList w WHERE w.product = :product AND w.user.username = :username")
	WishList findByProductAndUserId(@Param("product") Product product, @Param("username") String name);

	
	
}
