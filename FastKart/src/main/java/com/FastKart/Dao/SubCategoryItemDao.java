package com.FastKart.Dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.SubCategoryItemRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.subCategory;

@Service
public class SubCategoryItemDao {

	@Autowired private SubCategoryItemRepository categoryItemRepository;
	
	
	public boolean addsubCategoryitem(SubCategoryItem subCategoryItem) {
		subCategoryItem.setCreated_at(LocalDateTime.now());
		return this.categoryItemRepository.save(subCategoryItem)!=null;
	}
	
	public List<SubCategoryItem> showAllSubCategoryItem() {
		List<SubCategoryItem> findAllSubCategoryItem = (List<SubCategoryItem>) categoryItemRepository.findAll();
		return findAllSubCategoryItem;
	}
	
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
	
	
	public SubCategoryItem getSubCategoryItemById(int id) {		
		return categoryItemRepository.findById(id).get();
	}
	
	public void DeleteSubCategoryItem(int id) {
		categoryItemRepository.deleteById(id);
	}
	
}
