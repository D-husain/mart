package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;

@Service
public class cartDao {

	@Autowired private CartRepository cartRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private UserRepository userRepository;
	

	public void addToCart(int pid, int quntity, Principal principal) {
		Product product = productRepository.findById(pid).get();
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);

		if (product != null && user != null) {

			Cart existingCartItem = cartRepository.findByProductAndUser_Uid(product, user);
			if (existingCartItem != null) {
				int existingQty = existingCartItem.getQuntity();
				int updatedQty = existingQty + quntity;
				existingCartItem.setQuntity(updatedQty);
				existingCartItem.setTotal(product.getPrice() * updatedQty);
				cartRepository.save(existingCartItem);
			} else {
				Cart newCartItem = new Cart();
				newCartItem.setQuntity(quntity);
				newCartItem.setTotal(quntity * product.getPrice());
				newCartItem.setProduct(product);
				newCartItem.setUser(user);
				cartRepository.save(newCartItem);
			}
		}
	}

	public List<Cart> viewCart(Principal principal) {
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		return cartRepository.findByUser(user);
	}

	public int getTotalOfCart(List<Cart> cartList) {
		int subTotal = 0;
		for (Cart c : cartList) {
			subTotal += c.getQuntity() * c.getProduct().getPrice();
		}
		return subTotal;
	}

	public int getShippingTotal(List<Cart> cartList) {
		int shippingTotal = 0;
		int subTotalOfCart = getTotalOfCart(cartList);

		if (subTotalOfCart < 1) {
			for (Cart c : cartList) {
				shippingTotal += c.getShipping() * c.getQuntity();
			}
		} else if (subTotalOfCart >= 1 && subTotalOfCart <= 3000) {
			shippingTotal = 100;
		}
		return shippingTotal;
	}

	public int getTotalWithShipping(List<Cart> cartList, Cart cart) {
		int subTotalOfCart = getTotalOfCart(cartList);
		int shippingTotal = getShippingTotal(cartList);
		int grandTotal = subTotalOfCart + shippingTotal;
		return grandTotal;
	}
	
	public void updateCart(int id, int qty, int total) {
        Cart cartItem = cartRepository.findById(id).orElse(null);
        if (cartItem != null) {
            cartItem.setQuntity(qty);
            cartItem.setTotal(qty);
            cartRepository.save(cartItem);
        }
    }

	public boolean updateCart(Integer id, int qty) {
        Cart cartItem = cartRepository.getById(id);
        
        if (cartItem != null) {
            int total = cartItem.getProduct().getPrice() * qty;
            updateCart(id, qty, total);
            return true;
        }
        return false;
    }
	
	
	public void deleteCart(int id) {
		cartRepository.deleteById(id);
	}


}
