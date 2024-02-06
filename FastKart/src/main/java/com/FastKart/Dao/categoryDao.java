package com.FastKart.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CategoryRepository;
import com.FastKart.entities.Category;

@Service
public class categoryDao {
	
	@Autowired private CategoryRepository categoryRepository;
	
	public boolean addCategory(Category c) {
		 c.setCreated_at(LocalDateTime.now());
		return this.categoryRepository.save(c)!=null;
	}
	
	public List<Category> showAllCategory(){
		List<Category> allCategory = (List<Category>) categoryRepository.findAll();
		return allCategory;
	}
	
	public List<Category> ShowCategory(){
        return (List<Category>) categoryRepository.findAll();
    }

	public List<Category> getTopCategoriesOfTheWeek() {
		List<Category> categoriesOfTheWeek = categoryRepository.findTopCategoriesOfCurrentWeek();
		return categoriesOfTheWeek;
	}
	
	public Category getCategoryById(int id) {		
		return categoryRepository.findById(id).get();
	}
	
	public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCname(categoryName)
            .orElse(null); 
    }
	
	public void deleteCategory(int id) {
		categoryRepository.deleteById(id);
	}

}
