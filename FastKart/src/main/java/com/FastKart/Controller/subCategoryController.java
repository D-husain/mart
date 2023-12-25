package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.entities.subCategory;

@Controller
public class subCategoryController {

@Autowired
private subCategoryDao scDao;

@Autowired
private categoryDao cdao;
	
@PostMapping("/insertAddSubCategory")
public String addSubCategory(@ModelAttribute subCategory sc , @RequestParam("cat_image") MultipartFile file, @RequestParam("cid") int cid) {
	 
	
	try {
		
		if(file.isEmpty()) {
			System.out.println("Your File Is Empty");
		}
		else {
			sc.setSub_cat_image(file.getOriginalFilename());
			sc.setCategory(cdao.getCategory(cid));
			
			File saveFile = new ClassPathResource("static/assets1/images").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("file is uploaded");
		}
		
		subCategory addSubCategory = scDao.addSubCategory(sc);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	return "redirect:addSubCategory";
}


//============================================================================ DELETE SUBCATEGORY HANDLER ==============================================

@GetMapping("/deleteSubCategory/{id}")
public String deketeSubCategory(@PathVariable("id") Integer id, Model m) {
	
	scDao.deleteSubCategory(id);
	return "redirect:/subCategory";
	
	
	
}
}
