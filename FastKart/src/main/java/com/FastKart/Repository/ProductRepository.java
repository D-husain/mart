package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	List<Product> findProductByCategory(Category category);
	
	int countByCategory(Category category);
}
