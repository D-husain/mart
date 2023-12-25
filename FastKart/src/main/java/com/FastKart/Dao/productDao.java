package com.FastKart.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

@Service
public class productDao {
     
	@Autowired
	private ProductRepository productRepository;

	
	@Autowired
	private CategoryRepository categoryRepository;
//=================================================================== ADD PRODUCT METHOD ===============================================================
	public Product addProduct(Product p) {
		
		Product product = productRepository.save(p);
		
		return product;
	}
	
	
//================================================================ SHOW PRODUCT METHOD ================================================================
	
	public List<Product> showAllProduct(){
		
		List<Product> findAllProduct = (List<Product>) productRepository.findAll();
		
		return findAllProduct;
		
	}

//================================================================= DELETE PRODUCT BY ITS ID METHOD ====================================================
	
	public void deleteProduct(int id) {
	
		productRepository.deleteById(id);
	}

//=================================================================FIND CATEGORY WISE PRODUCT METHOD ===========================================================
	
		public List<Product> findProductByCategory(int id){
			
			// first we find category by its id
			Category findCategoryById =  categoryRepository.findById(id).get();
			
			List<Product> findProductByCategory = productRepository.findProductByCategory(findCategoryById); // this method created in productRepository
			return findProductByCategory;
			
		}
	
//=========================================================== Find PRODUCT BY ID ===================================================================
		
		public Product findProductById(int id){
			
			Product productById =productRepository.findById(id).get();
			return productById;
		}
		
		
		public List<Product> filterProductByCategory( Category category){
			
			if(category==null) {
				return   (List<Product>) productRepository.findAll();
			}
			else {
				return productRepository.findProductByCategory(category);
			}
		}
		
		/*
		 * public int countByCategory(int id) {
		 * 
		 * Category category = categoryRepository.findById(id).get();
		 * 
		 * return productRepository.countByCategory(category); }
		 */
		
} 
