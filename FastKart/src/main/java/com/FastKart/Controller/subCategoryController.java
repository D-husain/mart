package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.FileUpload.Upload_File;
import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

import jakarta.servlet.http.HttpSession;

@Controller
public class subCategoryController {

	@Autowired private subCategoryDao scDao;
	@Autowired private categoryDao cdao;
	@Autowired private Upload_File fileuploadhelper;

	String uploadCategory = "src/main/resources/static/assets/images/category/subcategory";

	@PostMapping("/subcategoryadd")
	public String categoryadd(@RequestParam("subcategoryImage") MultipartFile subcategoryImage,@RequestParam("subcategoryIcon") MultipartFile subcategoryIcon, 
			@RequestParam("subcategorynName") String subcategorynName,@RequestParam("category") String selectcategory,HttpSession hs) {
		Category c = cdao.getCategoryByName(selectcategory);
		
		subCategory subcategory = new subCategory();
		subcategory.setCategory(c);
		subcategory.setSubcname(subcategorynName);
		subcategory.setSubcimg(subcategoryImage.getOriginalFilename());
		subcategory.setSubcicon(subcategoryIcon.getOriginalFilename());

		try {
			if (!subcategoryImage.isEmpty()) {
				boolean isUploaded = fileuploadhelper.uploadFile(subcategoryImage, uploadCategory);
				fileuploadhelper.uploadFile(subcategoryIcon, uploadCategory);
				if (isUploaded) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = scDao.addsubCategory(subcategory);
		if (s) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/sub-category";
	}

//============================================================================ DELETE SUBCATEGORY HANDLER ==============================================

	@GetMapping("/deleteSubCategory/{id}")
	public String deketeSubCategory(@PathVariable("id") Integer id, Model m) {

		scDao.deleteSubCategory(id);
		return "redirect:/subCategory";

	}
}
