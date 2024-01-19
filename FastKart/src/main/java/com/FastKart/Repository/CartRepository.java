package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Cart;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;

import jakarta.transaction.Transactional;

public interface CartRepository extends CrudRepository<Cart, Integer> {

	List<Cart> findByUser(User user);

	int countByUser(User user);

	@Query("SELECT c FROM Cart c WHERE c.product = :product AND c.user = :user")
	Cart findByProductAndUser_Uid(Product product, User user);

	Cart getById(Integer id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Cart c WHERE c.user.id = :id")
	void deleteByCartuid(@Param("id") Integer id);


}
