package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.checkoutDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.CheckOut;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckOutController {

	@Autowired private checkoutDao ckdao;
	@Autowired private userDao udao;

	@PostMapping("/shipping-address")
	public String shippingAddress(@ModelAttribute("checkout") CheckOut checkOut, @RequestParam("addressId") int addressId, 
			Principal principal, RedirectAttributes redirAttrs, HttpSession session) {

		if (principal != null && udao.isUserLoggedIn(principal)) {
			CheckOut chOut = ckdao.saveUserShipping(checkOut, addressId, principal);

			if (chOut != null) {
				session.setAttribute("address", chOut);

				if ("Cash On Delivery".equals(chOut.getPaymentoption())) {
					return "redirect:/success-order";
				} else {
					return "redirect:/payment";
				}
			} else {
				redirAttrs.addFlashAttribute("error", "Something went wrong");
				return "redirect:/error";
			}
			
		} else {
			redirAttrs.addFlashAttribute("error", "User not logged in");
			return "redirect:/login";
		}
	}

}
