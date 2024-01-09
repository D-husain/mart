package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.subCategory;

public interface SubCategoryRepository extends CrudRepository<subCategory, Integer>{

	@Query("SELECT s FROM subCategory s WHERE s.category.id = :id")
	List<subCategory> findByCategoryid(@Param("id") int catId);
}
