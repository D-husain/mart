package com.FastKart.Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.SubCategoryItemRepository;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.subCategory;

@Service
public class SubCategoryItemDao {

	@Autowired private SubCategoryItemRepository categoryItemRepository;
	
	
	public List<SubCategoryItem> getSubcatItemBySubCategoryId(int subcatId) {
		List<SubCategoryItem> SubCategoryItemList = categoryItemRepository.findBysubCategoryid(subcatId);
		return SubCategoryItemList;
	}

	public Map<Integer, List<SubCategoryItem>> findSubCategoriesItemBysubCategoryId(List<subCategory> subcategories) {
		Map<Integer, List<SubCategoryItem>> subCategoryItemMap = new HashMap<>();

		for (subCategory subCategory : subcategories) {
			List<SubCategoryItem> subCategoriesItem = getSubcatItemBySubCategoryId(subCategory.getId());
			subCategoryItemMap.put(subCategory.getId(), subCategoriesItem);
		}
		return subCategoryItemMap;
	}
}
