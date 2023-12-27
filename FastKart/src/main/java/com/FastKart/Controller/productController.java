package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;


@Controller
public class productController {
	
	@Autowired
	private productDao pdao;
	
	@Autowired
	private categoryDao cdao;
	
	@Autowired
 private subCategoryDao scdao;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	/*
	 * @PostMapping("insertProduct") private String addProduct(@ModelAttribute
	 * Product product, @RequestParam("image") MultipartFile
	 * file, @RequestParam("cid") int cid, @RequestParam("scid") int scid) {
	 * 
	 * 
	 * try { if(file.isEmpty()) {
	 * 
	 * System.out.println("Your File is Empty"); }
	 * 
	 * else { product.setPimage(file.getOriginalFilename());
	 * 
	 * product.setCategory(cdao.getCategory(cid));
	 * product.setSubcategory(scdao.getSubCategory(scid));
	 * 
	 * File saveFile = new ClassPathResource("static/assets1/images").getFile();
	 * 
	 * Path path = Paths.get(saveFile.getAbsolutePath() +File.separator +
	 * file.getOriginalFilename());
	 * 
	 * Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	 * 
	 * System.out.println("File is uplosded"); }
	 * 
	 * pdao.addProduct(product); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * return "redirect:addProduct";
	 * 
	 * }
	 */
//============================================================= DELETE PRODUCT HANDLER =================================================================
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id, Model m) {
		
		pdao.deleteProduct(id);
		return "redirect:/product";
	}
	
//========================================================= FIND CATEGORY WISE PRODUCT HANDLER ========================================================	
	@GetMapping("/findProductByCategory/{id}")
	public String findProductByCategory(@PathVariable("id") int id, Model model) {
	    List<Product> productsByCategory = pdao.findProductByCategory(id);
	    model.addAttribute("productsByCategory", productsByCategory);
	    return "shop"; // Assuming your view name is "home.html"
	}
	
//=====================================================SHow PRODUCT DETAILS HANDLER====================================================================
	
	@GetMapping("/productDetails/{id}")
	public String productDetails(@PathVariable("id") int id, Model m) {
		Product findProductById = pdao.findProductById(id);
		
		m.addAttribute("productDetails", findProductById);
		return "productDetails";
	}

}
