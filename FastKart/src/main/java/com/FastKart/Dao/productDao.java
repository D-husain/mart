package com.FastKart.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.FastKart.Dto.ProductDTO;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

@Service
public class productDao {

	@Autowired private ProductRepository productRepository;
	@Autowired private CategoryRepository categoryRepository;

	public boolean addProduct(Product p) {
		boolean product = productRepository.save(p) != null;
		return product;
	}

	public List<Product> showLatestProducts() {
		List<Product> latestProducts = productRepository.findLatestProducts();
		return latestProducts;
	}

	public List<Product> showTopProductsToday() {
		List<Product> topProductsToday = productRepository.findTopProductsCreatedToday();
		return topProductsToday;
	}

	public List<Product> showPetFoodProducts() {
		List<Product> PetFoodProducts = productRepository.findByCategory("\r\nPet Foods");
		return PetFoodProducts;
	}
	
	public List<Product> showSnacksFoodProducts() {
		List<Product> SnacksFoodProducts = productRepository.findByCategory("\r\nBiscuits & Snacks");
		return SnacksFoodProducts;
	}
	
	public List<Product> viewProductsByCategoryName(String category) {
		return productRepository.findByProductCategoryName(category);
	}
	
	public List<Product> viewProductsBySubCategoryName(String subcategory) {
		return productRepository.findByProductSubCategoryName(subcategory);
	}
	
	public List<Product> viewProductsBySubCategoryItemName(String subcategoryitem) {
		return productRepository.findByProductSubCategoryItemName(subcategoryitem);
	}
	
	public List<Product> findProductByCategory(int id) {
		Category findCategoryById = categoryRepository.findById(id).get();
		List<Product> findProductByCategory = productRepository.findProductByCategory(findCategoryById); 
		return findProductByCategory;
	}

	public Product findProductById(int id) {
		Product productById = productRepository.findById(id).get();
		return productById;
	}

	public List<Product> filterProductByCategory(Category category) {
		if (category == null) {
			return (List<Product>) productRepository.findAll();
		} else {
			return productRepository.findProductByCategory(category);
		}
	}
	

	public List<Product> ProductSearch(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return (List<Product>) productRepository.findAll();
    }
	
	
	
	public List<Product> showAllProduct() {
		List<Product> findAllProduct = (List<Product>) productRepository.findAll();
		return findAllProduct;
	}
	
	public ProductDTO getapiProductById(int id) {
	    try {
	        Product product = productRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

	        return mapProductToDTO(product);
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
	    }
	}
	
	public ProductDTO mapProductToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		  productDTO.setId(product.getId());
		  productDTO.setPname(product.getPname());
		  productDTO.setPrice(product.getPrice());
		  productDTO.setDiscount_price(product.getDiscount_price());
		  productDTO.setDescription(product.getDescription());

		  if (product.getCategory() != null) {
		      productDTO.setCname(product.getCategory().getCname());
		  }
		  
		  if (product.getSubcategory() != null) {
		      productDTO.setSubcname(product.getSubcategory().getSubcname());
		  }

		  if (product.getDetails() != null) {
		      productDTO.setNetQuantity(product.getDetails().getNetQuantity());
		      productDTO.setStore_information(product.getDetails().getStore_information());
		      productDTO.setType(product.getDetails().getType());
		      productDTO.setSKU(product.getDetails().getSKU());
		      productDTO.setMFG(product.getDetails().getMFG());
		      productDTO.setStock(product.getDetails().getStock());
		      // ... set other details
		  }

		  if (product.getWeight() != null) {
		      productDTO.setKg(product.getWeight().getKg());
		  }

		  if (product.getProduct_image() != null) {
		      productDTO.setImage1(product.getProduct_image().getImage1());
		      productDTO.setImage2(product.getProduct_image().getImage2());
		      productDTO.setImage3(product.getProduct_image().getImage3());
		      productDTO.setImage4(product.getProduct_image().getImage4());
		  }

		  // Set timestamps
		  productDTO.setCreated_at(product.getCreated_at());
		  productDTO.setUpdated_at(product.getUpdated_at());
		  productDTO.setExpiry_at(product.getExpiry_at());

	    return productDTO;
	}

	
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	public List<Product> viewProductsByCategoryId(int cid) {
		return productRepository.findByProductCategoryId(cid);
	}

	public List<Product> filterByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(min, max);
    }


	
}
