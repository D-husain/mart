package com.FastKart.Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.SubCategoryRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

@Service
public class subCategoryDao {

	@Autowired private SubCategoryRepository subCategoryRepository;

	public subCategory addSubCategory(subCategory subcat) {
		subCategory subCategory = subCategoryRepository.save(subcat);
		return subCategory;
	}

	public List<subCategory> showAllSubCategory() {
		List<subCategory> findAllSubCategory = (List<subCategory>) subCategoryRepository.findAll();
		return findAllSubCategory;
	}

	/*
	 * public List<subCategory> findSubCategoriesByCategoryId(Integer categoryId) {
	 * Optional<Category> category = categoryRepository.findById(categoryId);
	 * 
	 * if (category.isPresent()) { return category.get().getSubcategory(); } else {
	 * return new ArrayList<>(); } }
	 */

	public List<subCategory> getSubcatByCategoryId(int catId) {
		List<subCategory> subCategoryList = subCategoryRepository.findByCategoryid(catId);
		return subCategoryList;
	}

	public Map<Integer, List<subCategory>> findSubCategoriesByCategoryId(List<Category> categories) {
		Map<Integer, List<subCategory>> subCategoryMap = new HashMap<>();

		for (Category category : categories) {
			List<subCategory> subCategories = getSubcatByCategoryId(category.getId());
			subCategoryMap.put(category.getId(), subCategories);
		}
		return subCategoryMap;
	}

	public subCategory getSubCategoryById(int id) {
		return subCategoryRepository.findById(id).get();
	}


	public void deleteSubCategory(int id) {
		subCategoryRepository.deleteById(id);
	}


}
