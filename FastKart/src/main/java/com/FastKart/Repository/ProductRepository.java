package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	List<Product> findProductByCategory(Category category);

	int countByCategory(Category category);

	@Query("SELECT p FROM Product p WHERE DATE(p.created_at) = CURRENT_DATE")
	List<Product> findTopProductsCreatedToday();

	@Query("SELECT p FROM Product p WHERE p.category.cname = :cname")
	List<Product> findByCategory(@Param("cname") String categoryName);


	@Query(value = "SELECT * FROM product WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) ORDER BY created_at DESC", nativeQuery = true)
	List<Product> findLatestProducts();



	
	
}
