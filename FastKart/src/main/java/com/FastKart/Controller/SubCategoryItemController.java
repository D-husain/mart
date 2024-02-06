package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.SubCategoryItemDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.subCategory;

import jakarta.servlet.http.HttpSession;

@Controller
public class SubCategoryItemController {

	@Autowired private subCategoryDao scDao;
	@Autowired private SubCategoryItemDao itemDao;


	@PostMapping("/subcategoryitemadd")
	public String categoryadd(@RequestParam("subcategorynitemName") String subcategorynitemName,@RequestParam("subcategory") String selectsubcategory,HttpSession hs) {
		subCategory subcat = scDao.getSubCategoryByName(selectsubcategory);
		
		SubCategoryItem subCategoryItem = new SubCategoryItem();
		subCategoryItem.setSubCat(subcat);
		subCategoryItem.setSubitemname(subcategorynitemName);

		boolean s = itemDao.addsubCategoryitem(subCategoryItem);
		if (s) {
			hs.setAttribute("message", "data successfully Inserted...");
		} else {
			hs.setAttribute("message", "data is not inserted");
		}

		return "redirect:/sub-category-item";
	}

}
