package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.SubCategoryItemDao;
import com.FastKart.Dao.UserCouponDao;
import com.FastKart.Dao.WishListDao;
import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.checkoutDao;
import com.FastKart.Dao.couponDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Category;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.Product;
import com.FastKart.entities.SubCategoryItem;
import com.FastKart.entities.User;
import com.FastKart.entities.UserCoupon;
import com.FastKart.entities.WishList;
import com.FastKart.entities.subCategory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UrlController {

	@Autowired private productDao pdao;
	@Autowired private categoryDao cdao;
	@Autowired private subCategoryDao subdao;
	@Autowired private SubCategoryItemDao subitemdao;
	@Autowired private UserRepository userRepository;
	@Autowired private cartDao cartdao;
	@Autowired private userDao udao;
	@Autowired private couponDao coupondao;
	@Autowired private WishListDao wdao;
	@Autowired private addressDao addao;
	@Autowired private CartRepository cartRepository;
	@Autowired private UserCouponDao usercdao;
	@Autowired private checkoutDao ckdao;
	@Autowired private WishListRepository wishListRepository;
	@Autowired private CheckOutRepository checkOutRepository;
	
	
//========================================================= HOME PAGE METHOD ==========================================================================	
	@GetMapping("/")
	public String index(Model model, Principal principal) {
		
		List<Product> showAllProduct = pdao.showAllProduct();
		model.addAttribute("products", showAllProduct);
		
		List<Product> showLatestProducts = pdao.showLatestProducts();
		model.addAttribute("Latestproducts", showLatestProducts);
		
		List<Product> showTopProductsToday = pdao.showTopProductsToday();
		model.addAttribute("Todayproducts", showTopProductsToday);

		List<Product> showPetFoodProducts = pdao.showPetFoodProducts();
		model.addAttribute("Petfood", showPetFoodProducts);
		
		List<Product> showSnacksFoodProducts = pdao.showSnacksFoodProducts();
		model.addAttribute("Snacksfood", showSnacksFoodProducts);
		
		
		return "index";
	}

//========================================================== ACCOUNT PAGES ======================================================================	

	@GetMapping("/sign-up")
	public String register(Model model) {
		return "account/sign-up";
	}

	@GetMapping("/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "account/login";
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

	@GetMapping("/forgot")
	public String forgot() {
		return "account/forgot";
	}
	
	@GetMapping("/varify-otp")
	public String varifyOtp(HttpSession session) {
		String email=(String) session.getAttribute("email");
		System.out.println("Session Email"+email);
		return "account/otp";
	}
	
	@GetMapping("reset_password")
	public String resetPassword() {
		return"account/reset-password";
	}

//======================================================= SHOP PAGES ==========================================================================================================	

	@GetMapping("/shop")
	public String shop() {

		return "shop";
	}

//======================================================= ABOUT PAGES ========================================================================================================

	@GetMapping("/about")
	public String about() {

		return "about-us";
	}

	// ======================================================= SEARCH PAGES ==================================================================================================

	@GetMapping("/search")
	public String search() {

		return "search";
	}

//======================================================= CONTACT PAGES ======================================================================================================
	@GetMapping("/contact")
	public String contact() {

		return "contact-us";
	}

	@GetMapping("/invoice")
	public String invoice() {

		return "invoice/invoice-1";
	}

	// ======================================================= WISHLIST PAGE METHOD ==========================================================================================
	@GetMapping("/wishlist")
	public String wishList(Principal principal, Model m) {
		if (principal != null) {
			List<WishList> viewWishList = wdao.viewWishList(principal);
			m.addAttribute("viewWishList", viewWishList);
			return "wishlist";
		} else {
			return "redirect:/login";
		}
	}

//======================================================= CART PAGE METHOD ============================================================================	

	@GetMapping("/cart")
	public String cart(Model model, Principal principal, Cart cart,HttpSession session) {

		if (principal != null) {
			List<Cart> viewCart = cartdao.viewCart(principal);
			model.addAttribute("viewcart", viewCart);

			int totalWithShipping = cartdao.getTotalWithShipping(viewCart, cart);
			model.addAttribute("totalWithShipping", totalWithShipping);
			
			UserCoupon coupon = (UserCoupon) session.getAttribute("coupon");
			if (coupon != null) {
				model.addAttribute("coupon", coupon);
				model.addAttribute("code", coupon.getCoupon());
			} else {
				model.addAttribute("coupon", "No Coupon Applied");
				model.addAttribute("code", "No Coupon Applied");
			}
			
			List<Coupon> couponList = coupondao.allCoupon();

			if (!couponList.isEmpty()) {
				int coupons = couponList.get(0).getDiscount();
				double discountPercentage = coupons;

				double discountAmount = totalWithShipping * (discountPercentage / 100);
				model.addAttribute("discountAmount", discountAmount);

				double discountedSubtotal = totalWithShipping - discountAmount;
				model.addAttribute("discountedSubtotal", discountedSubtotal);
			} else {
				model.addAttribute("discountAmount", 0.0); 
				model.addAttribute("discountedSubtotal", totalWithShipping); 
			}
			// Discount End

			return "cart";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/user-coupon")
	public String addCouponForUser(Principal principal, @ModelAttribute("usercoupon") UserCoupon userCoupon,
			RedirectAttributes redirAttrs, Model model, Cart cart, HttpSession session) {

		if (principal != null) {
			UserCoupon ucoupon = usercdao.addUserCoupon(userCoupon, principal);

			if (ucoupon != null) {
				List<Coupon> allCoupons = coupondao.allCoupon();
				
				if (!allCoupons.isEmpty()) {
					session.setAttribute("coupon", ucoupon);
					redirAttrs.addFlashAttribute("success", "Coupon applied successfully");
				} else {
					redirAttrs.addFlashAttribute("error", "No coupons available");
				}
			} else {
				redirAttrs.addFlashAttribute("error", "No coupon code provided");
			}
		}
		return "redirect:/cart";
	}
	


//======================================================= CHECKOUT PAGE METHOD ============================================================================
	@GetMapping("/checkout")
	public String checkOut(Model model, Principal principal, Cart cart, HttpSession session) {
		if (principal != null) {
			List<Address> showAllAddress = addao.showAllAddress(principal);
			model.addAttribute("userAddress", showAllAddress);

			List<Cart> carts = cartdao.viewCart(principal);
			
			int totalWithShipping = cartdao.getTotalWithShipping(carts, cart);
			model.addAttribute("totalWithShipping", totalWithShipping);

			UserCoupon coupon = (UserCoupon) session.getAttribute("coupon");
			if (coupon != null) {
				model.addAttribute("coupon", coupon);
				model.addAttribute("code", coupon.getCoupon());
			} else {
				model.addAttribute("coupon", "No Coupon Applied");
				model.addAttribute("code", "No Coupon Applied");
			}

			List<Coupon> couponList = coupondao.allCoupon();

			if (!couponList.isEmpty()) {
				int coupons = couponList.get(0).getDiscount();
				double discountPercentage = coupons;

				double discountAmount = totalWithShipping * (discountPercentage / 100);
				model.addAttribute("discountAmount", discountAmount);

				double discountedSubtotal = totalWithShipping - discountAmount;
				model.addAttribute("discountedSubtotal", discountedSubtotal);
			} else {
				model.addAttribute("discountAmount", 0.0); 
				model.addAttribute("discountedSubtotal", totalWithShipping); 
			}
			// Discount End

			return "checkout";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/payment")
	public String payment(Model model, Principal principal, Cart cart, HttpSession session) {

		if (principal != null) {
			
			List<Cart> carts = cartdao.viewCart(principal);
			
			int totalWithShipping = cartdao.getTotalWithShipping(carts, cart);
			model.addAttribute("totalWithShipping", totalWithShipping);
			
			List<CheckOut> shipping=ckdao.showShippingAddress(principal);
			model.addAttribute("shipping", shipping);

			UserCoupon coupon = (UserCoupon) session.getAttribute("coupon");
			if (coupon != null) {
				model.addAttribute("coupon", coupon);
				model.addAttribute("code", coupon.getCoupon());
			} else {
				model.addAttribute("coupon", "No Coupon Applied");
				model.addAttribute("code", "No Coupon Applied");
			}

			List<Coupon> couponList = coupondao.allCoupon();

			if (!couponList.isEmpty()) {
				int coupons = couponList.get(0).getDiscount();
				double discountPercentage = coupons;

				double discountAmount = totalWithShipping * (discountPercentage / 100);
				model.addAttribute("discountAmount", discountAmount);

				double discountedSubtotal = totalWithShipping - discountAmount;
				model.addAttribute("discountedSubtotal", discountedSubtotal);
			} else {
				model.addAttribute("discountAmount", 0.0); 
				model.addAttribute("discountedSubtotal", totalWithShipping);
			}
			// Discount End
			
			return "payment";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/success-order")
	public String success_order(Model model) {

		return "order-success";
	}
	
	@GetMapping("/order-cancel")
	public String order_cancel(Model model, Principal principal, Cart cart, HttpSession session) {
		if (principal != null) {
			List<Cart> viewCart = cartdao.viewCart(principal);
			model.addAttribute("viewcart", viewCart);

			int totalWithShipping = cartdao.getTotalWithShipping(viewCart, cart);
			model.addAttribute("totalWithShipping", totalWithShipping);

			List<Coupon> couponList = coupondao.allCoupon();

			if (!couponList.isEmpty()) {
				int coupons = couponList.get(0).getDiscount();
				double discountPercentage = coupons;

				double discountAmount = totalWithShipping * (discountPercentage / 100);
				model.addAttribute("discountAmount", discountAmount);

				double discountedSubtotal = totalWithShipping - discountAmount;
				model.addAttribute("discountedSubtotal", discountedSubtotal);
			} else {
				model.addAttribute("discountAmount", 0.0); 
				model.addAttribute("discountedSubtotal", totalWithShipping);
			}
			// Discount End
			
			return "order-cancel";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/order-tracking")
	public String order_tracking() {

		return "order-tracking";
	}

//======================================================= USERDASHBOARD PAGE METHOD ============================================================================

	@GetMapping("/dashboard")
	public String dasboard(Model model, Principal principal) {
		if (principal != null) {
			List<Address> showAllAddress = addao.showAllAddress(principal);
			model.addAttribute("userAddress", showAllAddress);
			
			List<User> showAllUser = udao.showAllUsers(principal);

			if (!showAllUser.isEmpty()) {
			    User user = showAllUser.get(0);

			    model.addAttribute("name", user.getUsername());
			    model.addAttribute("email", user.getEmail());

			    if (!user.getAddresses().isEmpty()) {
			        model.addAttribute("contact", user.getAddresses().get(0).getContact());
			        model.addAttribute("address", user.getAddresses().get(0).getAddress());
			    } else {
			        model.addAttribute("contact", "");
			        model.addAttribute("address", "");
			    }
			} else {
			    model.addAttribute("name", "");
			    model.addAttribute("email", "");
			    model.addAttribute("contact", "");
			    model.addAttribute("address", "");
			}

			
			List<CheckOut> shipping=ckdao.showShippingAddress(principal);
			model.addAttribute("shipping", shipping);
			
			return "user-dashboard";
		} else {
			return "redirect:/login";
		}

	}

//======================================================= PRODUCTDETAILS PAGE METHOD ============================================================================	
	@GetMapping("/product-details")
	public String productDetails() {

		return "product-details";
	}


//==================================================== COMFIRM ORDER PAGE HANDLER ================================================================
	@GetMapping("/comfirmOrder")
	public String comfirmOrder(Principal principal, Model m) {

		List<Cart> viewCart = cartdao.viewCart(principal);
		m.addAttribute("viewCart", viewCart);

		int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
		m.addAttribute("subTotalOfCart", subTotalOfCart);

		return "ORDER";

	}

//============================================================== ALL MODEL ==========================================================================

	@ModelAttribute("loggedInUser")
	public User getLoggedInUser(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userRepository.getUserByUserName(username);
		}
		return null;
	}

	@ModelAttribute("user")
	public User getDefaultUser() {
		return new User();
	}
	
	@ModelAttribute("Address")
	public Address getDefaultaddress() {
		return new Address();
	}

	@ModelAttribute("usercoupon")
	public UserCoupon getDefaultusercoupon() {
		return new UserCoupon();
	}
	
	@ModelAttribute("viewCart")
	public List<Cart> viewCart(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return cartRepository.findByUser(loggedInUser);
	}
	
	
	@ModelAttribute("/userAddress")
	public List<CheckOut> showAllAddress(Principal principal){
		User loggedInUser = getLoggedInUser(principal);
		return checkOutRepository.findByUser(loggedInUser);
	}

	@ModelAttribute("cartItemCount")
	public int countCartByUser(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);
		return cartRepository.countByUser(loggedInUser);
	}
	
	@ModelAttribute("subTotalOfCart")
	public int totalcart(Principal principal) {
		if (principal != null) {
			List<Cart> carts = cartdao.viewCart(principal);
			if (carts != null && !carts.isEmpty()) {
				return cartdao.getTotalOfCart(carts);
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	@ModelAttribute("shippingTotal")
	public int ShippingTotal(Principal principal) {
		if (principal != null) {
			List<Cart> carts = cartdao.viewCart(principal);
			if (carts != null && !carts.isEmpty()) {
				return cartdao.getShippingTotal(carts);
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	@ModelAttribute("wishList")
	public List<WishList> getUserWishList(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.findByUser(loggedInUser);
	}

	@ModelAttribute("wishListCount")
	public int countWishListByUser(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.countByUser(loggedInUser);
	}


	@ModelAttribute("categoryshow")
	public List<Category> viewcategory() {
		return cdao.showAllCategory();
	}
	
	@ModelAttribute("Weekcategory")
	public List<Category> TopWeekCategory() {
		return cdao.getTopCategoriesOfTheWeek();
	}

	@ModelAttribute("subcatshow")
	public List<subCategory> viewsubcategory() {
		return subdao.showAllSubCategory();
	}

	@ModelAttribute("subcategoryshow")
	public Map<Integer, List<subCategory>> showAllSubCategory() {
		List<Category> categories = viewcategory();
		Map<Integer, List<subCategory>> subCategoryMap = subdao.findSubCategoriesByCategoryId(categories);

		return subCategoryMap;
	}

	@ModelAttribute("subcategoryItemshow")
	public Map<Integer, List<SubCategoryItem>> showAllSubCategoryItem() {
		List<subCategory> subcategories = viewsubcategory();
		Map<Integer, List<SubCategoryItem>> subCategoryitemMap = subitemdao.findSubCategoriesItemBysubCategoryId(subcategories);

		return subCategoryitemMap;
	}

}
