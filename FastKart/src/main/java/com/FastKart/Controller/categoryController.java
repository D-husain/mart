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
import com.FastKart.FileUpload.Upload_File;
import com.FastKart.entities.Category;

import jakarta.servlet.http.HttpSession;


@Controller
public class categoryController {
	
	@Autowired
	private categoryDao cdao;
	@Autowired private Upload_File fileuploadhelper;
	
	String uploadCategory = "src/main/resources/static/assets/images/category";
	
	@PostMapping("/categoryadd")
	public String categoryadd(@RequestParam("categoryImage") MultipartFile categoryImage,@RequestParam("categoryIcon") MultipartFile categoryIcon, @RequestParam("categorynName") String categorynName,
			HttpSession hs) {

		Category category = new Category();
		category.setCname(categorynName);
		category.setCimage(categoryImage.getOriginalFilename());
		category.setCimage_icon(categoryIcon.getOriginalFilename());

		try {
			if (!categoryImage.isEmpty()) {
				boolean isUploaded = fileuploadhelper.uploadFile(categoryImage, uploadCategory);
				fileuploadhelper.uploadFile(categoryIcon, uploadCategory);
				if (isUploaded) {
					hs.setAttribute("message", "data successfully Inserted");
				}
			} else {
				hs.setAttribute("message", "file is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = cdao.addCategory(category);
		if (s) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/category";
	}
	
	
	
//===================================================== DELETE CATEGORY HANDLER =========================================================================
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model m) {
		
		cdao.deleteCategory(id);
		return "redirect:/category";
	}

}
             