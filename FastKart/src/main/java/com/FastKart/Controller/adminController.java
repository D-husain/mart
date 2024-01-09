package com.FastKart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.couponDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.Category;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.subCategory;

import jakarta.servlet.http.HttpServletRequest;

@Controller

public class adminController {

	@Autowired private categoryDao cdao;
	@Autowired private subCategoryDao scdao;
	@Autowired private productDao pdao;
	@Autowired private userDao udao;
	@Autowired private couponDao coupondao;
	
	
//================================================================================================================================================================================
	@GetMapping("/admin")
	public String adminDashboard() {
		return "admin/index";
	}
	
	@GetMapping("/admin-login")
	public String adminLogin() {
		return "admin/login";
	}
		
	@PostMapping("/login")
	public String login(HttpServletRequest request) {
	    // Your login logic
	    return "redirect:/admin"; // Redirect to a dashboard or another page after successful login
	}

//========================================================= Handler to get Admin addCategory page =========================================================	
		@GetMapping("/addCategory")
		public String addCategory() {
			
			return "admin/admin-addCategory";
		}
		
		
//========================================================= Handler to get Admin Category page ===========================================================
		@GetMapping("/category")
		public String category(Model m) {
			
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			
			return "admin/admin-category";
		}
	
//========================================================= Handler to get Admin addSubCategory page =====================================================
		@GetMapping("/addSubCategory")
		public String addSubCategory(Model m) {
			
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			return "admin/admin-addSubCategory";
		}
		
		
//========================================================= Handler to get Admin subCategory page=========================================================
	@GetMapping("/subCategory")
	public String subCategory(Model m) {
		 
		 List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		 m.addAttribute("subCategory", showAllSubCategory);
		
		
		return "admin/admin-subCategory";
	}
	
	
//========================================================= Handler to get Admin addProduct page =========================================================
		@GetMapping("/addProduct")
		public String addProduct(Model m) {
			
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			
			List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
			m.addAttribute("subCategory", showAllSubCategory);
			return "admin/admin-addProduct";
		}
		

//========================================================= Handler to get Admin Product page ============================================================
		@GetMapping("/product")
		public String product(Model m) {
			
			List<Product> showAllProduct = pdao.showAllProduct();
			m.addAttribute("product", showAllProduct);
			
			return "admin/admin-product";
		}
		
		
//========================================================= Handler to get Admin allUSer page ============================================================
		@GetMapping("/allUser")
		public String allUser(Model m) {
			
			List<User> showAllUser = udao.ShowAllUser();
			m.addAttribute("user", showAllUser);
			return "admin/admin-allUser";
		}


//========================================================== Handler to get Admin Create Coupon page =====================================================
		@GetMapping("/createCoupon")
		public String createCoupon() {
			
			return "admin/admin-createCoupon";
		}
		
		
//======================================================= Handler to get Admin allCoupon page ============================================================		
	@GetMapping("/allCoupon")
	public String allCoupon(Model m) {
		 
		List<Coupon> allCoupon = coupondao.allCoupon();
		m.addAttribute("allCoupon", allCoupon);
		return "admin/admin-allCoupon";
	}
	
	
//===================================================== Handler to get Admin allOrder page ==============================================================
	@GetMapping("/allOrder")
	public String allOrder() {
		
		return "admin/admin-allOrder";
	}
	
	
//====================================================== Handler to get Admin orderDetails page ==========================================================
	@GetMapping("/orderDetails")
	public String orderDetails() {
		
		return "admin/admin-orderDetails";
	}
	
	
//====================================================== Handler toget Admin orderTracking page =========================================================
	@GetMapping("/orderTracking")
	public String orderTracking() {
		
		return "admin/admin-orderTracking";
	}
	
	
//======================================================= Handler to get productReview Page ============================================================
	@GetMapping("/productReview")
	public String productReview() {
		
		return "admin/admin-productReview";
	}
	
	
	
		
	
}
