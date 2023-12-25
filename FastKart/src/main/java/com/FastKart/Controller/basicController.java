package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
public class basicController {

	@Autowired
	private productDao pdao;

	@Autowired
	private categoryDao cdao;

	@Autowired
	private userDao udao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private cartDao cartdao;

	@Autowired
	private WishListDao wdao;
	
	@Autowired
	private addressDao addao;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private WishListRepository wishListRepository;

//===================================================== HANDLER TO FETCH LOGIN USER====================================================================
	// in any situation i want to find something related to user (ex addToCart if
	// user login) than i will use this method
	// this method is check for user is login or not
	// also this handler used in all handler where you need to find that user is
	// login or not
	
	@ModelAttribute("loggedInUser")
	public User getLoggedInUser(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userRepository.getUserByUserName(username);
		}
		return null;
	}
	
//========================================================= HOME PAGE METHOD ==========================================================================	
	@GetMapping("/home")
	public String home(Model m, Principal principal) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		int id1 = 1;
		List<Product> product1 = pdao.findProductByCategory(id1);
		m.addAttribute("product1", product1);

		int id2 = 2;
		List<Product> product2 = pdao.findProductByCategory(id2);
		m.addAttribute("product2", product2);

		int id3 = 3;
		List<Product> product3 = pdao.findProductByCategory(id3);
		m.addAttribute("product3", product3);

		int id4 = 4;
		List<Product> product4 = pdao.findProductByCategory(id4);
		m.addAttribute("product4", product4);

		int id5 = 5;
		List<Product> product5 = pdao.findProductByCategory(id5);
		m.addAttribute("product5", product5);

		int id6 = 6;
		List<Product> product6 = pdao.findProductByCategory(id6);
		m.addAttribute("product6", product6);

		int id7 = 7;
		List<Product> product7 = pdao.findProductByCategory(id7);
		m.addAttribute("product7", product7);

		int id8 = 8;
		List<Product> product8 = pdao.findProductByCategory(id8);
		m.addAttribute("product8", product8);

		boolean userLoggedIn = udao.isUserLoggedIn(principal);
		m.addAttribute("userLoggedIn", userLoggedIn);

		User loggedInUser = getLoggedInUser(principal);
		m.addAttribute("loggedInUser", loggedInUser);

		return "home";
	}
//========================================================== REGISTER PAGE METHOD ======================================================================	

	@GetMapping("/register")
	public String register(Model m) {

		m.addAttribute("user", new User());
		return "register";
	}

//======================================================== LOGIN PAGE METHOD =========================================================================

	@GetMapping("/login")
	public String login() {

		return "login";
	}

//======================================================= SHOP PAGE METHOD ============================================================================	

	@GetMapping("/shop")
	public String shop() {

		return "shop";
	}

//======================================================= ABOUT PAGE METHOD ============================================================================

	@GetMapping("/about")
	public String about() {

		return "about-us";
	}

//======================================================= CONTACT PAGE METHOD ============================================================================
	@GetMapping("/contact")
	public String contact() {

		return "contact-us";
	}

//======================================================= VIEWCART PAGE METHOD ============================================================================	
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
	@GetMapping("/checkOut")
	public String checkOut() {

		return "checkOut";
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
	@GetMapping("/wishList")
	public String wishList(Principal principal, Model m) {
		if (principal != null) {
			List<WishList> viewWishList = wdao.viewWishList(principal);
			m.addAttribute("viewWishList", viewWishList);
			return "wishList";
		} else {
			return "redirect:/login";
		}
	}

//======================================================= USERDASHBOARD PAGE METHOD ============================================================================
	@GetMapping("/deshboard")
	public String userDashboard() {

		return " redirect:/user-dashboard";
	}

//======================================================= PRODUCTDETAILS PAGE METHOD ============================================================================	
	@GetMapping("/productDetails")
	public String productDetails() {

		return "productDetails";
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

	// The reason to create model is when i have to fetch one functionality in more
	// page i will use this model Instead of call method in every url

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

	// this model is for if wishList item ==0 than itemCount not show instead of 0
	@ModelAttribute("wishList")
	public List<WishList> getUserWishList(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.findByUser(loggedInUser);

	}

	// in any condition user is login and his cart item (Cart size) is 0 than we do
	// not show cartitemOut instead of 0
	@ModelAttribute("cart")
	public List<Cart> viewCart(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);

		return cartRepository.findByUser(loggedInUser);

	}

}
