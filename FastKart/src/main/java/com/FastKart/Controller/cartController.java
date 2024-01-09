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

import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.Cart;

@Controller
public class cartController {

	@Autowired private userDao udao;
	@Autowired private cartDao cartdao;

	@PostMapping("/addToCart")
	public String addToCart(@ModelAttribute Cart cart, @RequestParam("pid") int pid, @RequestParam("quntity") int quntity, Principal principal) {
		if (principal != null && udao.isUserLoggedIn(principal)) {
			cartdao.addToCart(cart, pid, quntity, principal);
			return "redirect:/cart";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/deleteCart/{id}")
	public String deleteCart(@PathVariable("id") int id, Model m) {
		cartdao.deleteCart(id);
		return "redirect:/cart";
	}

	@PostMapping("/updateCart")
	public String updateCart(@RequestParam("id") int id, @RequestParam("quantity") int quntity) {
		cartdao.updateCart(id, quntity);
		return "redirect:/cart";
	}

}
