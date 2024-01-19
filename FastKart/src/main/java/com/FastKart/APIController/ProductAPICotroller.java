package com.FastKart.APIController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.SubCategoryItemDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dto.ProductDTO;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.subCategory;

@Controller
public class ProductAPICotroller {
	
	@Autowired productDao pdao;
	@Autowired categoryDao cdao;
	@Autowired subCategoryDao subcatdao;
	@Autowired SubCategoryItemDao itemDao;
	//@Autowired private Upload_File fileuploadhelper;
	String uploadProduct = "src/main/resources/static/images/product";

	
	@GetMapping("/product/data")
	public ResponseEntity<List<ProductDTO>> getProductList() {
	    List<Product> products = pdao.showAllProduct();
	    List<ProductDTO> productDTOList = new ArrayList<>();

	    if (products != null && !products.isEmpty()) {
	        for (Product product : products) {
	            ProductDTO productDTO = pdao.mapProductToDTO(product);
	            productDTOList.add(productDTO);
	        }
	        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	
	@PostMapping("product/add")
	public ResponseEntity<Map<String, String>> productADD(@ModelAttribute("Product") Product product,@RequestParam("categoryId") int categoryId,
			@RequestParam("SubCategoryId") int SubCategoryId,@RequestParam("subCategoryItemId") int subCategoryItemId) {
	    Map<String, String> response = new HashMap<>();

	    Category c = cdao.getCategoryById(categoryId);
	    subCategory sc = subcatdao.getSubCategoryById(SubCategoryId);
	    SubCategoryItem item=itemDao.getSubCategoryItemById(subCategoryItemId);

	    product.setCategory(c);
	    product.setSubcategory(sc);
	    product.setSubCategoryItem(item);
	    product.setCreated_at(LocalDateTime.now());


	    boolean success = pdao.addProduct(product);
	    if (success) {
	        response.put("message", "Product successfully inserted");
	    } else {
	        response.put("message", "Data is not inserted");
	    }
	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("api/product/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
	    try {
	        ProductDTO productDTO = pdao.getapiProductById(id);

	        if (productDTO != null) {
	            return ResponseEntity.ok(productDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Void> deleteproduct(@PathVariable Integer id) {
		pdao.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
