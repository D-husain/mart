package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.FastKart.Dao.WishListDao;
import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

@Controller
public class UrlController {

	@Autowired private productDao pdao;
	@Autowired private categoryDao cdao;
	@Autowired private userDao udao;
	@Autowired private UserRepository userRepository;
	@Autowired private cartDao cartdao;
	@Autowired private WishListDao wdao;
	@Autowired private addressDao addao;
	@Autowired private CartRepository cartRepository;
	@Autowired private WishListRepository wishListRepository;

	
//========================================================= HOME PAGE METHOD ==========================================================================	
	@GetMapping("/")
	public String index(Model m, Principal principal) {
		return "index";
	}
	
//========================================================== ACCOUNT PAGES ======================================================================	

	@GetMapping("/sign-up")
	public String register(Model model) {
		return "account/sign-up";
	}

	@GetMapping("/login")
	public String login() {

		return "account/login";
	}

	@GetMapping("/forgot")
	public String forgot() {

		return "account/forgot";
	}
//======================================================= SHOP PAGES ============================================================================	

	@GetMapping("/shop")
	public String shop() {

		return "shop";
	}

//======================================================= ABOUT PAGES ============================================================================

	@GetMapping("/about")
	public String about() {

		return "about-us";
	}

//======================================================= CONTACT PAGES ============================================================================
	@GetMapping("/contact")
	public String contact() {

		return "contact-us";
	}
	
	@GetMapping("/invoice")
	public String invoice() {

		return "invoice/invoice-1";
	}

//======================================================= CART PAGE METHOD ============================================================================	
	
	@GetMapping("/cart")
	public String cart() {

		return "cart";
	}
	
	@GetMapping("/viewCart")
	public String showCart(Model m, Principal principal, Cart cart) {

		if (principal != null) {
			List<Cart> viewCart = cartdao.viewCart(principal);
			m.addAttribute("cart", viewCart);
			
			  int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
			   m.addAttribute("subTotalOfCart", subTotalOfCart);
			   
			 int totalWithShipping = cartdao.getTotalWithShipping(viewCart, cart);
			 m.addAttribute("totalWithShipping", totalWithShipping);
			 
			 int shippingTotal = cartdao.getShippingTotal(viewCart);
			 m.addAttribute("shippingTotal", shippingTotal);

			return "Cart";
		} else {
			// Redirect to the login page
			return "redirect:/login";
		}
	}

//======================================================= CHECKOUT PAGE METHOD ============================================================================
	@GetMapping("/checkout")
	public String checkOut() {

		return "checkout";
	}
	
	@GetMapping("/payment")
	public String payment() {

		return "payment";
	}
	
	@GetMapping("/success-order")
	public String success_order() {

		return "order-success";
	}
	
	@GetMapping("/order-tracking")
	public String order_tracking() {

		return "order-tracking";
	}



	
	@GetMapping("/checkOut1")
	public String CHECKOUT(Model m, Principal principal) {
		 
		if(principal!=null) {
		List<Address> showAllAddress = addao.showAllAddress(principal);
		m.addAttribute("showAllAddress", showAllAddress);
		
		
	   List<Cart> viewCart = cartdao.viewCart(principal);
	   m.addAttribute("viewCart", viewCart);
	   
	   
	   int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
	   m.addAttribute("subTotalOfCart", subTotalOfCart);
		return "CKECHOUT";
		}
		else {
			return  null; 
		}
		

		
	}
//======================================================= WISHLIST PAGE METHOD ============================================================================	
	@GetMapping("/wishlist")
	public String wishList(Principal principal, Model m) {
		return "wishlist";
		/*
		 * if (principal != null) { List<WishList> viewWishList =
		 * wdao.viewWishList(principal); m.addAttribute("viewWishList", viewWishList);
		 * return "wishlist"; } else { return "redirect:/login"; }
		 */
	}

//======================================================= USERDASHBOARD PAGE METHOD ============================================================================
	@GetMapping("/deshboard")
	public String userDashboard() {

		return " redirect:/user-dashboard";
	}
	
	@GetMapping("/dashboard")
	public String dasboard(Model m, Principal principal) {
		return "user-dashboard";
	}

//======================================================= PRODUCTDETAILS PAGE METHOD ============================================================================	
	@GetMapping("/product-details")
	public String productDetails() {

		return "product-details";
	}
	
//======================================================= GET ALL PRODUCT PAGE METHOD ============================================================================
	@GetMapping("/getProduct")
	public String product(Model m) {

		List<Product> showAllProduct = pdao.showAllProduct();
		m.addAttribute("showAllProduct", showAllProduct);

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("showAllCategory", showAllCategory);

		return "product";
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

	@ModelAttribute("cartItemCount")
	public int countCartByUser(Principal principal) {

		User loggedInUser =getLoggedInUser(principal);
		return cartRepository.countByUser(loggedInUser);
	}

	@ModelAttribute("wishListCount")
	public int countWishListByUser(Principal principal) {

		User loggedInUser =getLoggedInUser(principal);
		return wishListRepository.countByUser(loggedInUser);
	}

	@ModelAttribute("wishList")
	public List<WishList> getUserWishList(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.findByUser(loggedInUser);

	}

	@ModelAttribute("cart")
	public List<Cart> viewCart(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return cartRepository.findByUser(loggedInUser);
	}

}
