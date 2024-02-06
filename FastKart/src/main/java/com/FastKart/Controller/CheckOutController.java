package com.FastKart.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.OrderDao;
import com.FastKart.Dao.checkoutDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.OrderDetailsRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
import com.FastKart.entities.Product;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckOutController {

	@Autowired private checkoutDao ckdao;
	@Autowired private userDao udao;
	@Autowired ProductRepository productrepo;
	@Autowired private OrderDao orderDao;
	@Autowired private OrderDetailsRepository orderDetailsRepository;
	

	@PostMapping("/shipping-address")
	public String shippingAddress(@ModelAttribute("checkout") CheckOut checkOut, @RequestParam("addressId") int addressId, 
			Principal principal, RedirectAttributes redirAttrs, HttpSession session,
			@RequestParam("price") Double[] prices, @RequestParam("qty") Integer[] quantities,
			@RequestParam("totals") Double[] totals, @RequestParam("pid") Integer[] productIds,
			@RequestParam("charge") String charge,
			@RequestParam("discount") double discount, @RequestParam("total") double total,
			@RequestParam("amount") double amount) {

		if (principal != null && udao.isUserLoggedIn(principal)) {
			CheckOut chOut = ckdao.saveUserShipping(checkOut, addressId, principal);
			
			if (chOut != null) {
				session.setAttribute("address", chOut);

				if ("Cash On Delivery".equals(chOut.getPaymentoption())) {
					
					List<OrderDetails> orderDetailsList = new ArrayList<>();

					for (int i = 0; i < productIds.length; i++) {
						Double totalPrice = totals[i];
						Integer quantity = quantities[i];
						Double price = prices[i];
						Integer productId = productIds[i];

						OrderDetails orderProduct = new OrderDetails();
						orderProduct.setPrice(price);
						orderProduct.setQty(quantity);
						orderProduct.setTotal(totalPrice);

						Product product = productrepo.getById(productId);
						orderProduct.setProduct(product);

						orderDetailsList.add(orderProduct);
					}
					Orders order = new Orders();
					order.setAmount(amount);
					order.setCharge(charge);
					order.setDiscount(discount);
					order.setTotal(total);
					order.setCheckOut(checkOut);

					order.setOrderList(orderDetailsList);
					
					int shippingId=chOut.getId();	
					
					Orders savedOrder = orderDao.saveUserOrders(order, shippingId, principal);

					for (OrderDetails orderDetails : orderDetailsList) {
						orderDetails.setOrder(savedOrder);
						orderDetailsRepository.save(orderDetails);
					}

					// session.removeAttribute("coupon");
					redirAttrs.addFlashAttribute("success", "Order sent successfully.");
					
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
