package com.FastKart.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CategoryRepository;
import com.FastKart.entities.Category;

@Service
public class categoryDao {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
//===================================================== Add Category method ==============================================================================	
	public Category addCategory(Category c) {
		 
		Category category = categoryRepository.save(c);
		
		return category;
	}
	
//==================================================== Show All Category =================================================================================	
	public List<Category> showAllCategory(){
		
		List<Category> allCategory = (List<Category>) categoryRepository.findAll();
		
		return allCategory;
	}
	
	
//================================================= Find  Category by its id ===========================================================================	
	public Category getCategory(int id) {		
		return categoryRepository.findById(id).get();
	}
	

//================================================== Delete category method ============================================================================
	
	public void deleteCategory(int id) {
		
		categoryRepository.deleteById(id);
	}

	

}
