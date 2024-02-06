package com.FastKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query(value = "SELECT c FROM Category c WHERE YEARWEEK(c.created_at) = YEARWEEK(NOW())")
    List<Category> findTopCategoriesOfCurrentWeek();

	@Query("SELECT c FROM Category c WHERE c.cname = ?1")
	Optional<Category> findByCname(String categoryName);


}
