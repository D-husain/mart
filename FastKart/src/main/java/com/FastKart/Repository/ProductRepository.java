package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	List<Product> findProductByCategory(Category category);

	int countByCategory(Category category);

	//@Query("SELECT p FROM Product p WHERE DATE(p.created_at) = CURRENT_DATE ORDER BY created_at DESC")
	//List<Product> findTopProductsCreatedToday();
	
	 @Query("SELECT p FROM Product p WHERE DATE(p.created_at) = CURRENT_DATE ORDER BY p.created_at DESC")
	    List<Product> findTopProductsCreatedToday();

	@Query("SELECT p FROM Product p WHERE p.category.cname = :cname")
	List<Product> findByCategory(@Param("cname") String categoryName);
	
	@Query("SELECT p FROM Product p WHERE p.subcategory.subcname = :subcname")
	List<Product> findBySubCategory(@Param("subcname") String subcategoryName);

	@Query(value = "SELECT * FROM product WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) ORDER BY created_at DESC", nativeQuery = true)
	List<Product> findLatestProducts();

	@Query("SELECT p FROM Product p where p.category.id = ?1")
	List<Product> findByProductCategoryId(int cid);

	Product getById(Integer proId);

	@Query("SELECT p FROM Product p where p.category.cname = ?1")
	List<Product> findByProductCategoryName(String category);
	
	@Query("SELECT p FROM Product p where p.subcategory.subcname = ?1")
	List<Product> findByProductSubCategoryName(String subcategory);
	
	@Query("SELECT p FROM Product p where p.subCategoryItem.subitemname = ?1")
	List<Product> findByProductSubCategoryItemName(String subcategoryitem);

	@Query("SELECT p FROM Product p WHERE p.pname LIKE %?1%")
	public List<Product> search(String keyword);
	
	@Query("SELECT p FROM Product p ORDER BY p.price ASC")
	List<Product> sortByPriceLowToHigh();

	@Query("SELECT p FROM Product p ORDER BY p.price DESC")
	List<Product> sortByPriceHighToLow();

	@Query("SELECT p FROM Product p ORDER BY p.pname DESC")
	List<Product> sortByProductNameZ();

	@Query("SELECT p FROM Product p ORDER BY p.pname ASC")
	List<Product> sortByProductNameA();

	List<Product> findByPriceBetween(int min, int max);

	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.subCategoryItem.subitemname=?2")
	List<Product> findProductsByCategoryAndsubcategoryitem(int categoryId, String subcategoryitem);




	
	
}
