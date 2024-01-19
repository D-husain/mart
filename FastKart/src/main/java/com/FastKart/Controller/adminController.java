package com.FastKart.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.FastKart.Dao.OrderDao;
import com.FastKart.Dao.SubCategoryItemDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.couponDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.Category;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
import com.FastKart.entities.Product;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.User;
import com.FastKart.entities.subCategory;

@Controller

public class adminController {

	@Autowired private categoryDao cdao;
	@Autowired private subCategoryDao scdao;
	@Autowired private SubCategoryItemDao subitmdao;
	@Autowired private OrderDao orderdao;
	@Autowired private productDao pdao;
	@Autowired private userDao udao;
	@Autowired private couponDao coupondao;
	@Autowired private OrderDao odao;

	
//================================================================================================================================================================================
	@GetMapping("/admin")
	public String adminDashboard() {
		return "admin/index";
	}
	
	@GetMapping("/admin-login")
	public String adminLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "admin/login";
		}
		return "redirect:/admin";
	}
	
//========================================================= Handler to get Admin Categorys page ===========================================================
		@GetMapping("/category")
		public String category(Model m) {
			return "admin/category/category";
		}
		
		@GetMapping("/sub-category")
		public String sub_cat(Model model) {
			return"admin/category/subcat/sub-category";
		}
		
		@GetMapping("/sub-category-item")
		public String sub_cat_item(Model model) {
			return"admin/category/subcat/subitem/sub-category-item";
		}
	
	
//========================================================= Handler to get Admin Product page ============================================================
		@GetMapping("/product")
		public String product(Model m) {
			
			List<Product> showAllProduct = pdao.showAllProduct();
			m.addAttribute("product", showAllProduct);
			
			return "admin/product/products";
		}
		
		@GetMapping("add-new-product")
		public String addproduct() {
			return"admin/product/add-new-product";
		}
		
//========================================================= Handler to get Admin allUSer page ============================================================
		@GetMapping("/all-users")
		public String allUser(Model model) {

				List<User> users = udao.ShowAllUser();
				model.addAttribute("users", users);

				Map<Integer, List<Orders>> orderMap = new HashMap<>();
				for (User user : users) {
					List<Orders> orderDetails = odao.viewOrders(user.getId());
					orderMap.put(user.getId(), orderDetails);
					System.out.println("order size:-" + orderMap.size());
				}
				model.addAttribute("orderMap", orderMap);


			return "admin/user/all-users";
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
	@GetMapping("/order-list")
	public String allOrder(Model model) {
		List<Orders> orders = orderdao.ShowUserOrders();
		model.addAttribute("orders", orders);

		Map<Integer, List<OrderDetails>> orderDetailsMap = orderdao.getOrderDetailsMap(orders);
		model.addAttribute("orderDetailsMap", orderDetailsMap);
		return "admin/order/order-list";
	}
	
	@GetMapping("/order-detail")
	public String allOrderdetail() {
		return "admin/order/order-detail";
	}
	
	@GetMapping("/track-order")
	public String allOrdertracking() {
		return "admin/order/order-tracking";
	}
	
	
//====================================================== Handler to get Admin orderDetails page ==========================================================
	@GetMapping("/orderDetails")
	public String orderDetails() {
		
		return "admin/admin-orderDetails";
	}
	
	
//======================================================= Handler to get productReview Page ============================================================
	@GetMapping("/productReview")
	public String productReview() {
		
		return "admin/admin-productReview";
	}
	
	
//========================================================================================================================================================
	@ModelAttribute("Product")
	public Product getDefaultproduct() {
		return new Product();
	}
	
	
	@ModelAttribute("category")
	public List<Category> viewcategory() {
		return cdao.showAllCategory();
	}
	
	@ModelAttribute("subcat")
	public List<subCategory> viewsubcategory() {
		return scdao.showAllSubCategory();
	}

	@ModelAttribute("subcategoryshow")
	public Map<Integer, List<subCategory>> showAllSubCategory() {
		List<Category> categories = viewcategory();
		Map<Integer, List<subCategory>> subCategoryMap = scdao.findSubCategoriesByCategoryId(categories);

		return subCategoryMap;
	}

	@ModelAttribute("subcategoryItemshow")
	public Map<Integer, List<SubCategoryItem>> showAllSubCategoryItem() {
		List<subCategory> subcategories = viewsubcategory();
		Map<Integer, List<SubCategoryItem>> subCategoryitemMap = subitmdao.findSubCategoriesItemBysubCategoryId(subcategories);

		return subCategoryitemMap;
	}
	
	
	
		
	
}
