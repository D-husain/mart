package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.SubCategoryItem;

public interface SubCategoryItemRepository extends JpaRepository<SubCategoryItem, Integer> {

	@Query("SELECT s FROM SubCategoryItem s WHERE s.subCat.id = :id")
	List<SubCategoryItem> findBysubCategoryid(@Param("id") int subcatId);
}
