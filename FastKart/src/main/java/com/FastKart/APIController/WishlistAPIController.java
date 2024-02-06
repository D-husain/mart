package com.FastKart.APIController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.WishListDao;
import com.FastKart.Dto.WishlistDTO;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

@Controller
@RequestMapping("/api/wishlist")
public class WishlistAPIController {

	@Autowired private ProductRepository productrepo;
	@Autowired private WishListRepository wishlistrepo;
	@Autowired private UserRepository userRepository;
	@Autowired private WishListDao wishListDao;

	@PostMapping("/addToWishlist")
	public ResponseEntity<String> addToWishlist(@RequestParam int pid, Principal principal) {
	    if (principal == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to add items to the wishlist.");
	    } else {
	        try {
	            String username = principal.getName(); // Get the username from the Principal

	            if (pid <= 0) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID.");
	            }

	            Product product = productrepo.findById(pid).orElse(null);

	            if (product == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
	            }

	            // Check if the product is already in the user's wishlist
	            WishList existingWishlistItem = wishlistrepo.findByProductAndUserId(product, username);

	            if (existingWishlistItem != null) {
	                return ResponseEntity.status(HttpStatus.CONFLICT).body("Product is already in your wishlist.");
	            } else {
	                WishList newWishlistItem = new WishList();
	                newWishlistItem.setProduct(product);
	                User user = userRepository.getUserByUserName(username); // Fetch User entity using username
	                newWishlistItem.setUser(user); // Set the User object in the Wishlist

	                wishlistrepo.save(newWishlistItem);
	                return ResponseEntity.status(HttpStatus.OK).body("Product added successfully to the wishlist.");
	            }
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
	        } catch (DataAccessException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accessing data.");
	        }
	    }
	}



	@GetMapping("/data")
	public ResponseEntity<List<WishlistDTO>> getWishlistData(Principal principal) {
		try {
			if (principal == null) {
				return ResponseEntity.badRequest().build();
			}

			List<WishList> wishlist = wishListDao.viewWishList(principal);

			if (wishlist == null || wishlist.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			List<WishlistDTO> wishlistDTOList = wishlist.stream().map(wishlistItem -> {
				WishlistDTO wishlistDTO = new WishlistDTO();
				wishlistDTO.setId(wishlistItem.getId());

				Product product = wishlistItem.getProduct();
				if (product != null) {
					wishlistDTO.setProductid(product.getId());
					//wishlistDTO.setImage1(product.getProduct_image().getImage1()); 
					wishlistDTO.setPname(product.getPname()); // Corrected field naming convention
					wishlistDTO.setPrice(product.getPrice());
					wishlistDTO.setDiscount_price(product.getDiscount_price());
				}

				wishlistDTO.setName(principal.getName()); // Set user ID for each wishlist item

				return wishlistDTO;
			}).collect(Collectors.toList());

			return ResponseEntity.ok(wishlistDTOList); // Return wishlist data with OK status
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
		}
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> getuserWishlistsize(Principal principal) {

		if (principal == null) {
			return ResponseEntity.badRequest().build();
		}

		List<WishList> wishlistItems = wishListDao.viewWishList(principal);

		return ResponseEntity.ok(wishlistItems.size());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteWishlist(@PathVariable Integer id) {
		wishListDao.deleteWishList(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product delete successfully.");
	}
}
