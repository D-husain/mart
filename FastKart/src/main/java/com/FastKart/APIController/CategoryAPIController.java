package com.FastKart.APIController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.FastKart.Dao.categoryDao;
import com.FastKart.entities.Category;

@Controller
public class CategoryAPIController {
	
	@Autowired private categoryDao cdao;

	
	@GetMapping("/category/data")
	public ResponseEntity<List<Category>> getCategoryList() {
		return new ResponseEntity<List<Category>>(cdao.ShowCategory(), HttpStatus.OK);
	}
}
