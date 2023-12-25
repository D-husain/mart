package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.WishListDao;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

@Controller
public class WishListController {

	@Autowired
	private WishListDao wdao;

//=============================================== ADD TO WISHIST HANDLER IF USER IS LOGIN ===========================================================
	@PostMapping("/addToWishList")
	public String addToWishList(@ModelAttribute WishList w,Principal principal, @RequestParam("pid") int pid) {
		
		if(principal!=null) {
			
			WishList addToWishList = wdao.addToWishList(w, pid, principal);
			return "redirect:/wishList";
		}
		 else {
		        // Redirect to the login page
		        return "redirect:/login";
		    }
	}
	
//============================================== DELETE WISHLIST ITEM HANDLER =========================================================================
	
	@GetMapping("/deleteWishList/{id}")
	public String deleteWishList(@PathVariable("id") int id, Model m ) {
		
		wdao.deleteWishList(id);
		return "redirect:/wishList";
	}
	
	
	
	
}
