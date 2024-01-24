package com.FastKart.APIController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Dto.CartDTO;
import com.FastKart.Repository.CartRepository;
import com.FastKart.entities.Cart;

@Controller
@RequestMapping("/api/cart")
public class CartAPIController {
	
	@Autowired private cartDao cartdao;
	@Autowired private CartRepository cartrepo;
	@Autowired private userDao udao;


	@PostMapping("/addToCart")
	public ResponseEntity<String> addToCart(@RequestParam int pid, @RequestParam int qty, Principal principal) {
	    if (principal == null || !udao.isUserLoggedIn(principal)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to add items to your cart.");
	    } else {
	        try {
	            cartdao.addToCart(pid, qty, principal);

	            return ResponseEntity.status(HttpStatus.OK).body("Product added successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to the cart.");
	        }
	    }
	}

	
	@GetMapping("/data")
	public ResponseEntity<List<CartDTO>> getCartList( Principal principal) {
		if (principal == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Cart> cart = cartdao.viewCart(principal);

		if (cart == null || cart.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<CartDTO> cartDTO = cart.stream().map(carts -> {
			CartDTO cdto = new CartDTO();
			cdto.setId(carts.getId());
			cdto.setQuntity(carts.getQuntity());
			cdto.setTotal(carts.getTotal());

			if (carts.getProduct() != null) {
				cdto.setProductid(carts.getProduct().getId());
				//cdto.setImage1(carts.getProduct().getProduct_image().getImage1());
				cdto.setPname(carts.getProduct().getPname());
				cdto.setDiscount_price(carts.getProduct().getDiscount_price());
				cdto.setPrice(carts.getProduct().getPrice());
			}

			if (carts.getUser() != null) {
				cdto.setUserid(carts.getUser().getId());
			}

			return cdto;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(cartDTO); // Return cart data with OK status
	}
	
	
	
	@PostMapping("/updateCart")
	public ResponseEntity<String> updateCarts(@RequestBody CartDTO cartUpdateDTO) {
	    if (cartUpdateDTO.getId() == 0) {
	        return ResponseEntity.badRequest().body("'id' parameter is required.");
	    }

	    Cart cartItem = cartrepo.getById(cartUpdateDTO.getId());

	    if (cartItem != null) {
	        cartdao.updateCart(cartUpdateDTO.getId(), cartUpdateDTO.getQuntity());
	        return ResponseEntity.ok("Product updated successfully.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
	    }
	}


	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCart(@PathVariable Integer id) {
		cartdao.deleteCart(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product delete successfully.");
	}

}
