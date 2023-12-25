package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.CouponRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;


@Service
public class cartDao {
	
@Autowired
private  CartRepository cartRepository;

@Autowired
private ProductRepository productRepository;

@Autowired
private UserRepository userRepository;


@Autowired
private CouponRepository couponRepository;
	
public Cart addToCart( Cart cart ,int pid, int quntity, Principal principal) {
	
// find pid for check that product is null or not
Product product = 	productRepository.findById(pid).get();

// find that user is login or not, if user is null in that case we redirect user to login page.
String name =	principal.getName();
User user = userRepository.getUserByUserName(name);


   	

   if (product != null && user != null) {
	   
	   cart.setQuntity(quntity);
	   cart.setTotal(quntity*product.getPrice());
	 
	   cart.setProduct(product);
       cart.setUser(user);
       Cart saveCarts = cartRepository.save(cart);
         
       return saveCarts;
       
   } else {
	   
	   
       return null;
   }
   
}

//================================================================== VIEW CART METHOD IF USER LOGIN ====================================================


public List<Cart> viewCart(Principal principal){
	
	String name = principal.getName();
	
	User user = userRepository.getUserByUserName(name);
	
	
	 return cartRepository.findByUser(user);

}


//===================================================== DELETE CART METHOD =============================================================================
public void deleteCart(int id) {
	
	cartRepository.deleteById(id);
}


/*
 * public int countCartItem(User user) {
 * 
 * return cartRepository.countByUser(user); }30
 */

//==================================================== GET TOTAL OF CART =========================================================================
public int getTotalOfCart(List<Cart> cartList) {
	
	int subTotal=0;
	
	for(Cart c : cartList) {
		
		
		subTotal += c.getQuntity()*c.getProduct().getPrice() ;
		
		
	}
	return subTotal;
}

//================================================== GET TOTAL OF SHIPPING ======================================================================


public int getShippingTotal(List<Cart> cartList) {
	
	int shippingTotal = 0;
	
	int subTotalOfCart = getTotalOfCart(cartList);
	
	for(Cart c : cartList) {
		
		if(subTotalOfCart<1000) {
		shippingTotal += c.getShipping()*c.getQuntity();
		}
		else { 	
			shippingTotal =0;
		}
		
	}
	return shippingTotal;
}


//================================================== GET GRANDTOTAL(SUBTOTAL + SIPPING) METHOD =================================================

public int getTotalWithShipping(List<Cart> cartList, Cart cart) {
   
	
	int subTotalOfCart = getTotalOfCart(cartList);
    
    int shippingTotal = getShippingTotal(cartList);
    
   

    // Add shipping charge to the subtotal to get the total
    int grandTotal = subTotalOfCart + shippingTotal;

    return grandTotal;
}

//=================================================== UPDATE QUNTITY & TOTAL BY CART ID=========================================================

public void updateCart(int id, int quntity) {
	
	Cart cart = cartRepository.findById(id).get();
	
	if(cart!= null) {
		
		cart.setQuntity(quntity);
		
		cart.setTotal(quntity*cart.getProduct().getPrice());
		
		cartRepository.save(cart);
	}
	else {
		
	}
	
}


//==================================================== GET DISCOUNT METHOD ======================================================================

public void getDiscount( int couponId, List<Cart> cartList) {
	
	
    int subTotalOfCart = getTotalOfCart(cartList);

    Coupon coupon = couponRepository.findById(couponId).orElse(null);
    
    Cart c  = new Cart();

        if (coupon != null) {
            // Get the discount percentage from the coupon
            int discount = coupon.getDiscount();

            // Calculate the discounted total
            int discountedTotal = subTotalOfCart - discount;

            // Set the total with discount to the cart
           
        } else {
            // No discount applied
    
        }

        // Save the updated cart
        cartRepository.save(c);
    }
}


