package com.FastKart.APIController;

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

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dto.ProductDTO;
import com.FastKart.FileUpload.Upload_File;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;
import com.FastKart.entities.subCategory;

@Controller
public class ProductAPICotroller {
	
	@Autowired productDao pdao;
	@Autowired categoryDao cdao;
	@Autowired subCategoryDao subcatdao;
	@Autowired private Upload_File fileuploadhelper;
	String uploadProduct = "src/main/resources/static/images/product";

	
	@GetMapping("/product/data")
	public ResponseEntity<List<ProductDTO>> getProductList() {
		  List<Product> products = pdao.showAllProduct();
		  List<ProductDTO> Product = new ArrayList<>();
		  
		  for (Product product : products) {
			  ProductDTO productDTO = new ProductDTO();
		        productDTO.setId(product.getId());
		        productDTO.setPname(product.getPname());
		        productDTO.setCname(product.getCategory().getCname());
		        Product.add(productDTO);
		    }
		  
		  return new ResponseEntity<>(Product, HttpStatus.OK);
	}
	
	
	@PostMapping("product/add")
	public ResponseEntity<Map<String, String>> productADD(@ModelAttribute Product product, 
	                                                      @RequestParam("cid") int cid,
	                                                      @RequestParam("scid") int scid) {
	    Map<String, String> response = new HashMap<>();

	    Category c = cdao.getCategory(cid);
	    subCategory sc = subcatdao.getSubCategoryById(scid);

	    product.setCategory(c);
	    product.setSubcategory(sc);


	    boolean success = pdao.addProduct(product);
	    if (success) {
	        response.put("message", "Product successfully inserted");
	    } else {
	        response.put("message", "Data is not inserted");
	    }
	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO productDTO = pdao.getapiProductById(id);
        return ResponseEntity.ok(productDTO);
    }
	
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Void> deleteproduct(@PathVariable Integer id) {
		pdao.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
