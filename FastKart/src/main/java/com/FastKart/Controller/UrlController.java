package com.FastKart.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.FastKart.Dao.OrderDao;
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
import com.FastKart.Dto.ProductDTO;
import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Category;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Contact;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
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
	@Autowired private OrderDao orderdao;
	@Autowired private ProductRepository productrepo;
	
	
	
//========================================================= HOME PAGE METHOD ==========================================================================	
	@GetMapping("/")
	public String index(Model model, Principal principal) {
		
		List<Product> showAllProduct = pdao.showAllProduct();
		model.addAttribute("products", showAllProduct);
		
		List<Product> showLatestProducts = pdao.showLatestProducts();
		model.addAttribute("Latestproducts", showLatestProducts);
		
		List<Product> showTopProductsToday = pdao.showTopProductsToday();
		model.addAttribute("Todayproducts", showTopProductsToday);

		List<Product> showfruitsandvegetablesProducts = pdao.showFruitsandVegetablesProducts();
		model.addAttribute("fruitandvegetable", showfruitsandvegetablesProducts);
		
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

//======================================================= SHOP PAGES =======================================================================================	

	@GetMapping("/shop")
	public String shop(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "subcategoryitem", required = false) String subcategoryitem,Model model) {

		List<Product> productList = null;

	    if (category == null && subcategoryitem == null) {
	        productList = pdao.showAllProduct();
	    }
	    else if (category != null && subcategoryitem == null) {
	        productList = pdao.viewProductsByCategoryName(category);
	    }
	    else if(category == null && subcategoryitem != null) {
	    	productList = pdao.viewProductsBySubCategoryItemName(subcategoryitem);
	    }
	    
	    model.addAttribute("product", productList);
	    
		return "shop";
	}
	
	@GetMapping("api/shop")
	public ResponseEntity<List<ProductDTO>> shop(
	        @RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId,
	        @RequestParam(name = "subcategoryitem", required = false) String subcategoryitem) {

	    List<ProductDTO> productDTOList = new ArrayList<>();
	    List<Product> productList;

	    if (categoryId == 0 && subcategoryitem == null) {
	        productList = pdao.showAllProduct();
	    } else if (categoryId != 0 && subcategoryitem == null) {
	        productList = pdao.viewProductsByCategoryId(categoryId);
	    } else if (categoryId == 0 && subcategoryitem != null) {
	        productList = pdao.viewProductsBySubCategoryItemName(subcategoryitem);
	    } else {
	    	 productList = pdao.getProductsByCategorysubcategoryitem(categoryId, subcategoryitem);
	    }

	    if (productList != null) {
	        for (Product product : productList) {
	            ProductDTO productDTO = pdao.mapProductToDTO(product);
	            productDTOList.add(productDTO);
	        }
	    }
	    
	    int productCount = productDTOList.size();

	    System.out.println("Filtered products count: " + productCount);

	    return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	}

	
	@GetMapping("/api/sort")
	public ResponseEntity<List<ProductDTO>> sortProducts(@RequestParam("sort") String sortType) {
	    List<ProductDTO> productDTOList = new ArrayList<>();
	    List<Product> sortedProducts;

	    switch (sortType) {
	        case "low":
	            sortedProducts = productrepo.sortByPriceLowToHigh();
	            break;
	        case "high":
	            sortedProducts = productrepo.sortByPriceHighToLow();
	            break;
	        case "aToz":
	            sortedProducts = productrepo.sortByProductNameA();
	            break;
	        case "zToa":
	            sortedProducts = productrepo.sortByProductNameZ();
	            break;
	        default:
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    for (Product product : sortedProducts) {
	        ProductDTO productDTO = pdao.mapProductToDTO(product);
	        productDTOList.add(productDTO);
	    }

	    if (!productDTOList.isEmpty()) {
	        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/filterByPriceRange")
    public ResponseEntity<List<ProductDTO>> filterByPriceRange( @RequestParam(name = "min", required = false, defaultValue = "0") int min,
            @RequestParam(name = "max", required = false, defaultValue = "9999999") int max) {
		 List<ProductDTO> productDTOList = new ArrayList<>();
        
        List<Product> filteredProducts = pdao.filterByPriceRange(min, max);
        
        for (Product product : filteredProducts) {
	        ProductDTO productDTO = pdao.mapProductToDTO(product);
	        productDTOList.add(productDTO);
	    }

        if (!productDTOList.isEmpty()) {
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	
//======================================================= PRODUCTDETAILS PAGE METHOD ========================================================================	

	@GetMapping("/product-details/{id}")
	public String productDetails(@PathVariable("id") int id, Model model, Principal principal) {
		Product product = pdao.findProductById(id);
		model.addAttribute("product", product);

		if (product != null) {
			List<Product> relatedProducts = pdao.viewProductsByCategoryId(product.getCategory().getId());
			model.addAttribute("related", relatedProducts);
			
			// Using UriComponentsBuilder to construct the URL
	        String productDetailsUrl = UriComponentsBuilder.fromPath("http://localhost:8080/product-details/{id}").buildAndExpand(product.getId()).toUriString();
	        
	        model.addAttribute("productDetailsUrl", productDetailsUrl);
		}
		return "product-details";
	}

	
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
	    Product product = pdao.findProductById(id);

	    if (product != null) {
	        ProductDTO productDTO = pdao.mapProductToDTO(product);
	        return new ResponseEntity<>(productDTO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

//======================================================= ABOUT PAGES ========================================================================================================

	@GetMapping("/about")
	public String about() {

		return "about-us";
	}

	// ======================================================= SEARCH PAGES ==================================================================================================

	@GetMapping("/search")
	public String search(@Param("keyword") String keyword, Model model, HttpSession session) {
		List<Product> products = pdao.ProductSearch(keyword);
		model.addAttribute("product", products);
		model.addAttribute("keyword", keyword);
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
			RedirectAttributes redirAttrs, Model model,HttpSession session) {

		if (principal != null) {
			UserCoupon ucoupon = usercdao.addUserCoupon(userCoupon, principal);
			List<Coupon> allCoupons = coupondao.allCoupon();

			if (ucoupon != null) {
				session.setAttribute("coupon", ucoupon);
				redirAttrs.addFlashAttribute("success", "Coupon applied successfully");
			}
			else if(allCoupons.isEmpty()) {
				redirAttrs.addFlashAttribute("error", "No coupons available");
			}
			else {
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
			
			List<CheckOut> shipping=ckdao.showShippingAddressCreatedAtAfter(principal);
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
	public String success_order(Model model,Principal principal) {
		 List<Orders> orders = orderdao.showUsersOrder(principal);
		    model.addAttribute("orders", orders);
		    
		    Map<Integer, List<OrderDetails>> orderDetailsMap = orderdao.getOrderDetailsMap(orders);
	        model.addAttribute("orderDetailsMap", orderDetailsMap);

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

	@GetMapping("/order-tracking/{id}")
	public String order_tracking(@PathVariable("id") int id, Model model, Principal principal) {
		Orders order=orderdao.findOrdersById(id);
		model.addAttribute("order", order);
		return "order-tracking";
	}

//======================================================= USERDASHBOARD PAGE METHOD ============================================================================

	@GetMapping("/dashboard")
	public String dasboard(Model model, Principal principal) {
		if (principal != null) {
			List<Address> showAllAddress = addao.showAllAddress(principal);
			model.addAttribute("userAddress", showAllAddress);
			
			List<Address> defaultaddress=addao.getDefaultShippingAddress(principal);
			model.addAttribute("defaultaddress", defaultaddress);
			
			List<User> showAllUser = udao.showAllUsers(principal);

			if (!showAllUser.isEmpty()) {
			    User user = showAllUser.get(0);

			    model.addAttribute("name", user.getUsername());
			    model.addAttribute("email", user.getEmail());

			    if (!user.getAddresses().isEmpty()) {
			        model.addAttribute("contact", user.getAddresses().get(0).getContact());
			        model.addAttribute("address", user.getAddresses().get(0).getAddress());
			    } else {
			        model.addAttribute("contact", "Contact is empty");
			        model.addAttribute("address", "Address is empty");
			    }
			} else {
			    model.addAttribute("name", "Name is empty");
			    model.addAttribute("email", "Email is empty");
			    model.addAttribute("contact", "Contact is empty");
			    model.addAttribute("address", "Address is empty");
			}

			
			List<CheckOut> shipping=ckdao.showShippingAddressCreatedAtAfter(principal);
			model.addAttribute("shipping", shipping);
			
			 List<Orders> orders = orderdao.showUsersOrder(principal);
			 model.addAttribute("orders", orders);
			    
			  Map<Integer, List<OrderDetails>> orderDetailsMap = orderdao.getOrderDetailsMap(orders);
		      model.addAttribute("orderDetailsMap", orderDetailsMap);
			
			return "user-dashboard";
		} else {
			return "redirect:/login";
		}

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
	
	@ModelAttribute("orders")
	public Orders getDefaultorders() {
		return new Orders();
	}
	
	@ModelAttribute("contact")
	public Contact getDefaultcontact() {
		return new Contact();
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
