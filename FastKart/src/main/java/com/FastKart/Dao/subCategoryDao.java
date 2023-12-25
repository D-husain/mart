package com.FastKart.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.SubCategoryRepository;
import com.FastKart.entities.subCategory;

@Service
public class subCategoryDao {
     
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
//================================================== method for add subCateory ==========================================================================	
	public subCategory addSubCategory(subCategory sc) {
		
		subCategory subCategory = subCategoryRepository.save(sc);
		
		return subCategory;
	}
	
	
//================================================= Method for show All subCategory ====================================================================
	public List<subCategory> showAllSubCategory(){
		
		List<subCategory> findAllSubCategory = (List<subCategory>) subCategoryRepository.findAll();
		
		return findAllSubCategory;
	
	}
//============================================ Method for get subCategory bt its id ====================================================================	
 public subCategory getSubCategory(int id) {
	 
	return subCategoryRepository.findById(id).get();
 }
 
 
//======================================= Method For detele subCategory  ==============================================================================
 
 public void deleteSubCategory(int id) {
	 
	 subCategoryRepository.deleteById(id);
 }
	
	
	
}
