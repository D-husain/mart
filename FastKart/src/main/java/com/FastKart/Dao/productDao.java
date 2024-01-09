package com.FastKart.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

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

	
	
	
	public List<Product> showAllProduct() {
		List<Product> findAllProduct = (List<Product>) productRepository.findAll();
		return findAllProduct;
	}
	
	public ProductDTO getapiProductById(int id) {
        Product product = null;
		try {
			product = productRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
        return mapProductToDTO(product);
    }
	
	public ProductDTO mapProductToDTO(Product product) {
	    ProductDTO productDTO = new ProductDTO();
	    productDTO.setId(product.getId());
	    productDTO.setPname(product.getPname());
	    productDTO.setCname(product.getCategory().getCname());
	    // ... continue setting other basic fields

	    // Set the additional attributes in ProductDTO
	    productDTO.setSubcname(product.getSubcategory().getSubcname());
	    productDTO.setDiscount_price(product.getDiscount_price());
	    productDTO.setKg(product.getWeight().getKg());
	    productDTO.setStore_information(product.getDetails().getStore_information());
	    productDTO.setType(product.getDetails().getType());
	    productDTO.setSKU(product.getDetails().getSKU());
	    productDTO.setMFG(product.getDetails().getMFG());
	    productDTO.setStock(product.getDetails().getStock());
	    // ... set other additional fields

	    // Set image URLs or paths
	    productDTO.setImage1(product.getProduct_image().getImage1());
	    productDTO.setImage2(product.getProduct_image().getImage2());
	    productDTO.setImage3(product.getProduct_image().getImage3());
	    productDTO.setImage4(product.getProduct_image().getImage4());

	    // Set timestamps
	    productDTO.setCreated_at(product.getCreated_at());
	    productDTO.setUpdated_at(product.getUpdated_at());
	    productDTO.setExpiry_at(product.getExpiry_at());

	    return productDTO;
	}

	
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	
}
