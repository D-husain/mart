package com.FastKart.APIController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.SubCategoryItemDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dto.CategoryDTO;
import com.FastKart.Dto.SubCategoryDTO;
import com.FastKart.Dto.SubCategoryItemDTO;
import com.FastKart.FileUpload.Upload_File;
import com.FastKart.entities.Category;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.subCategory;

import jakarta.servlet.http.HttpSession;

@Controller
public class CategoryAPIController {
	
	@Autowired private categoryDao cdao;
	@Autowired private subCategoryDao subdao;
	@Autowired private SubCategoryItemDao itemdao;
	@Autowired private Upload_File fileuploadhelper;
	String uploadCategory = "src/main/resources/static/assets/images/category";
	String uploadSubCategory = "src/main/resources/static/assets/images/category/subcategory";

	
	@PostMapping("api/categoryadd")
	public ResponseEntity<Map<String, String>> categoryadd(@RequestParam("categoryImage") MultipartFile categoryImage,
			@RequestParam("categoryIcon") MultipartFile categoryIcon, @RequestParam("categorynName") String categorynName) {
		Map<String, String> response = new HashMap<>();
		Category category = new Category();
		category.setCname(categorynName);
		category.setCimage(categoryImage.getOriginalFilename());
		category.setCimage_icon(categoryIcon.getOriginalFilename());

		try {
			if (!categoryImage.isEmpty()|| categoryIcon.isEmpty()) {
				boolean isUploaded = fileuploadhelper.uploadFile(categoryImage, uploadCategory);
				fileuploadhelper.uploadFile(categoryIcon, uploadCategory);
				if (isUploaded) {
					response.put("message", "Image successfully inserted");
				}
			} else {
				response.put("message", "File is empty");
			}
		} catch (Exception e) {
			response.put("message", "An error occurred while uploading the file");
			e.printStackTrace();
		}

		boolean success = cdao.addCategory(category);
		if (success) {
			response.put("message", "Category successfully inserted");
		} else {
			response.put("message", "Data is not inserted");
		}

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/category/data")
	public ResponseEntity<List<CategoryDTO>> getCategoryList() {
	    List<CategoryDTO> categoryDTOList = convertToCategoryDTOList(cdao.ShowCategory());
	    return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
	}

	private List<CategoryDTO> convertToCategoryDTOList(List<Category> categories) {
	    List<CategoryDTO> categoryDTOList = new ArrayList<>();
	    for (Category category : categories) {
	        CategoryDTO categoryDTO = new CategoryDTO();
	        categoryDTO.setId(category.getId());
	        categoryDTO.setCname(category.getCname());
	        categoryDTO.setCimage(category.getCimage());
	        categoryDTO.setCimage_icon(category.getCimage_icon());
	        categoryDTO.setCreated_at(category.getCreated_at());
	        categoryDTO.setUpdated_at(category.getUpdated_at());
	        categoryDTOList.add(categoryDTO);
	    }
	    return categoryDTOList;
	}
	
	//============================================================== Sub Category =========================================================================================
	
	@PostMapping("/api/subcategoryadd")
	public ResponseEntity<Map<String, String>> subcategoryadd(@RequestParam("subcategoryImage") MultipartFile subcategoryImage,@RequestParam("subcategoryIcon") MultipartFile subcategoryIcon, 
			@RequestParam("subcategorynName") String subcategorynName,@RequestParam("category") String selectcategory,HttpSession hs) {
		Category c = cdao.getCategoryByName(selectcategory);
		Map<String, String> response = new HashMap<>();
		subCategory subcategory = new subCategory();
		subcategory.setCategory(c);
		subcategory.setSubcname(subcategorynName);
		subcategory.setSubcimg(subcategoryImage.getOriginalFilename());
		subcategory.setSubcicon(subcategoryIcon.getOriginalFilename());

		try {
			if (!subcategoryImage.isEmpty()  || subcategoryIcon.isEmpty()) {
				boolean isUploaded = fileuploadhelper.uploadFile(subcategoryImage, uploadSubCategory);
				fileuploadhelper.uploadFile(subcategoryIcon, uploadCategory);
				if (isUploaded) {
					response.put("message", "Image successfully inserted");
				}
			} else {
				response.put("message", "File is empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean s = subdao.addsubCategory(subcategory);
		if (s) {
			response.put("message", "SubCategory successfully inserted");
		} else {
			response.put("message", "Data is not inserted");
		}

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/getSubcategories")
	public ResponseEntity<List<SubCategoryDTO>> getSubcategories(@RequestParam Integer categoryId) {
	    List<subCategory> subcategories = subdao.getSubcatByCategoryId(categoryId);

	    List<SubCategoryDTO> subcategoryDTOs = subcategories.stream()
	            .map(subCategory -> new SubCategoryDTO(subCategory.getId(), subCategory.getSubcname(),
	            		subCategory.getSubcimg(), subCategory.getSubcicon(), categoryId, subCategory.getCategory().getCname()))
	            .collect(Collectors.toList());

	    return ResponseEntity.ok(subcategoryDTOs);
	}

	
	@GetMapping("/subcategory/data")
	public ResponseEntity<List<SubCategoryDTO>> getSubCategoryList() {
	    List<SubCategoryDTO> subcategoryDTOList = convertTosubCategoryDTOList(subdao.showAllSubCategory());
	    return new ResponseEntity<>(subcategoryDTOList, HttpStatus.OK);
	}

	private List<SubCategoryDTO> convertTosubCategoryDTOList(List<subCategory> subcategories) {
	    List<SubCategoryDTO> SubcategoryDTOList = new ArrayList<>();
	    for (subCategory subcategory : subcategories) {
	        SubCategoryDTO SubcategoryDTO = new SubCategoryDTO(subcategory.getId(), subcategory.getSubcname(), 
	        		subcategory.getSubcimg(), subcategory.getSubcicon(), subcategory.getCategory().getId(), subcategory.getCategory().getCname());
	        
	        SubcategoryDTOList.add(SubcategoryDTO);
	    }
	    return SubcategoryDTOList;
	}

	//============================================================== Sub Category Item =====================================================================================
	
	@PostMapping("/api/subcategoryitemadd")
	public ResponseEntity<Map<String, String>> subcategoryItemadd(@RequestParam("subcategorynitemName") String subcategorynitemName,
			@RequestParam("subcategory") String selectsubcategory,HttpSession hs) {
		
		subCategory subcat = subdao.getSubCategoryByName(selectsubcategory);
		Map<String, String> response = new HashMap<>();
		SubCategoryItem subCategoryItem = new SubCategoryItem();
		subCategoryItem.setSubCat(subcat);
		subCategoryItem.setSubitemname(subcategorynitemName);

		boolean s = itemdao.addsubCategoryitem(subCategoryItem);
		if (s) {
			response.put("message", "SubCategoryItem successfully inserted");
		} else {
			response.put("message", "Data is not inserted");
		}

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/getSubcategoriesitem")
	public ResponseEntity<List<SubCategoryItemDTO>> getSubcategoriesitem(@RequestParam Integer subcategoryId) {
	    List<SubCategoryItem> subcategoriesitem = itemdao.getSubcatItemBySubCategoryId(subcategoryId);

	    List<SubCategoryItemDTO> subCategoryItemDTOs = subcategoriesitem.stream()
	            .map(subCategoryitem -> new SubCategoryItemDTO(subCategoryitem.getId(), subCategoryitem.getSubitemname(), 
	            		subCategoryitem.getSubCat().getId(), subCategoryitem.getSubCat().getSubcname(), subCategoryitem.getSubCat().getSubcimg(), 
	            		subCategoryitem.getSubCat().getSubcicon(), subCategoryitem.getSubCat().getCategory().getId(), subCategoryitem.getSubCat().getCategory().getCname()))
	            .collect(Collectors.toList());

	    return ResponseEntity.ok(subCategoryItemDTOs);
	}
	
	
	@GetMapping("/subcategory/item/data")
	public ResponseEntity<List<SubCategoryItemDTO>> getSubCategoryItemList() {
	    List<SubCategoryItemDTO> subcategoryDTOList = convertTosubCategoryItemDTOList(itemdao.showAllSubCategoryItem());
	    return new ResponseEntity<>(subcategoryDTOList, HttpStatus.OK);
	}

	private List<SubCategoryItemDTO> convertTosubCategoryItemDTOList(List<SubCategoryItem> subcategoriesitem) {
	    List<SubCategoryItemDTO> SubcategoryItemDTOList = new ArrayList<>();
	    for (SubCategoryItem subcategoryitem : subcategoriesitem) {
	    	SubCategoryItemDTO SubcategoryitemDTO = new SubCategoryItemDTO(subcategoryitem.getId(), subcategoryitem.getSubitemname(), 
	    			subcategoryitem.getSubCat().getId(), subcategoryitem.getSubCat().getSubcname(), subcategoryitem.getSubCat().getSubcimg(), 
	    			subcategoryitem.getSubCat().getSubcicon(), subcategoryitem.getSubCat().getCategory().getId(), subcategoryitem.getSubCat().getCategory().getCname());
	        
	    	SubcategoryItemDTOList.add(SubcategoryitemDTO);
	    }
	    return SubcategoryItemDTOList;
	}
	
	
	@DeleteMapping("/subcategory/item/delete/{id}")
	public ResponseEntity<Void> deleteSubCategoryItem(@PathVariable Integer id) {
		itemdao.DeleteSubCategoryItem(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
