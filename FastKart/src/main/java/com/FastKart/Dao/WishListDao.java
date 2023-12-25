package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

@Service
public class WishListDao {

@Autowired
private WishListRepository wishListRepository;	

@Autowired
private ProductRepository productRepository;

@Autowired
private UserRepository userRepository;

	
//================================================================ ADD TO WISHLIST METHOD IF USER LOGIN ================================================
public WishList addToWishList(WishList w, @RequestParam("pid") int pid, Principal principal) {
	
 Product product = productRepository.findById(pid).get();
 
 String name = principal.getName();
 User user = userRepository.getUserByUserName(name);
 
 if(user!=null && product!= null) {
	 
	 w.setProduct(product);
	 w.setUser(user);
	 WishList wishList = wishListRepository.save(w);
	 
	 return wishList;
 }
 else {
	 return null;
 }
	   
	
}

//===================================== SHOW WISHLIST ACCORDING LOGIN USER METHOD =====================================================================
public List<WishList> viewWishList(Principal principal){
	
	String name = principal.getName();
	
	User user = userRepository.getUserByUserName(name);
	return wishListRepository.findByUser(user);
	
}


//========================================= DELETE WISHLIST ITEM METHOD ===============================================================================


public void deleteWishList(int id) {
	
	wishListRepository.deleteById(id);
}

}


