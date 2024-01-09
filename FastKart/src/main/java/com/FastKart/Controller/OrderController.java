package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.OrderDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.Orders;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired private OrderDao orderDao;
	@Autowired private userDao udao;

	@PostMapping("/user-orders")
	public String createOrder(@RequestBody List<Integer> productIds, Principal principal, RedirectAttributes redirAttrs, HttpSession session,
			@ModelAttribute("orders") Orders orders, @RequestParam("shippingId") int shippingId ) {
		
		if (principal != null && udao.isUserLoggedIn(principal)) {
			Orders order = orderDao.SaveOrder(productIds, shippingId, principal);

			if (orders != null) {
				session.setAttribute("order", order);

				return "redirect:/success-order";
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
