package com.FastKart.Controller;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.FastKart.Dao.OrderDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.OrderDetailsRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
import com.FastKart.entities.Product;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired private OrderDao orderDao;
	@Autowired private userDao udao;
	@Autowired ProductRepository productrepo;
	@Autowired private OrderDetailsRepository orderDetailsRepository;

	@PostMapping("/user-orders")
	public String createOrder(@RequestParam("price") Double[] prices, @RequestParam("qty") Integer[] quantities,
			@RequestParam("totals") Double[] totals, @RequestParam("pid") Integer[] productIds, Principal principal,
			RedirectAttributes redirAttrs, HttpSession session, @RequestParam("charge") String charge,
			@RequestParam("discount") double discount, @RequestParam("total") double total,
			@RequestParam("amount") double amount,@RequestParam("shippingId") int shippingId) {

		if (principal != null && udao.isUserLoggedIn(principal)) {
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

			order.setOrderList(orderDetailsList);

			Orders savedOrder = orderDao.saveUserOrders(order, shippingId, principal);

			for (OrderDetails orderDetails : orderDetailsList) {
				orderDetails.setOrder(savedOrder);
				orderDetailsRepository.save(orderDetails);
			}

			// session.removeAttribute("coupon");
			redirAttrs.addFlashAttribute("success", "Order sent successfully.");

			return "redirect:/success-order";
		} else {
			redirAttrs.addFlashAttribute("error", "User not logged in");
			return "redirect:/login";
		}
	}
	
	
	
	@GetMapping("/invoice/{id}")
	public ModelAndView generatePdf(@PathVariable int id, HttpServletResponse response) {
		List<OrderDetails> order = orderDao.getOrderDetailsByOrderId(id);

		if (order == null) {
			return new ModelAndView("orderNotFound");
		}
		String htmlContent = generateOrderHtml(order);
		try {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			renderer.createPDF(pdfStream);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=order_" + id + ".pdf");

			response.getOutputStream().write(pdfStream.toByteArray());
			response.getOutputStream().flush();
			
			renderer.finishPDF();
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("pdfGenerationError");
		}

		return null;
	}

	private String generateOrderHtml(List<OrderDetails> order) {
	    String jspContent = "<html>\n" + "<head>\n" + "<style>"
	            + "body { font-family: Arial, sans-serif; } table { width: 100%; border-collapse: collapse; } th, td { border: 1px solid #0da487; padding: 8px; text-align: left; }.store{color: #0da487; font-family: cursive; text-align: center; display: flex; justify-content: center; align-items: center;}.invoice{ text-align: center; font-family: serif; } th { background-color: rgb(13 164 135 / 49%); }"
	            + "</style>" + "</head>\n" + "<body>\n"
	            + "<div class='store'><h1 class='store'> Fastkart</h1></div>"
	            + "<h2 class='invoice'>Order Invoice</h2>" + "<p>Order Id: " + order.get(0).getOrder().getId() + "</p>"
	            + "<p>Order Date: " + order.get(0).getOrder().getOrderdate() + "</p>" + "<p>Customer Name: "
	            + order.get(0).getOrder().getUser().getAddresses().get(0).getName()
	            + "</p>" + "<p>Email: " + order.get(0).getOrder().getCheckOut().getAddress().getUser().getEmail() + "</p>"
	            + "<p>Billing Address: " + order.get(0).getOrder().getCheckOut().getAddress().getAddress() + "</p>"
	            + "<p>Phone Number: " + order.get(0).getOrder().getCheckOut().getAddress().getContact() + "</p>"
	            + "<table>"
	            + "<tr>" + "<th>ID</th>" + "<th>Product Name</th>" + "<th>Price</th>" + "<th>Qty</th>"
	            + "<th>Total</th>" + "</tr>";

	    int id = 1;
	    for (OrderDetails product : order) {
	        String productInfo = "<tr>" + "<td>" + id + "</td>" + "<td>" + product.getProduct().getPname() + "</td>" + "<td>"
	                + product.getPrice() + "</td>" + "<td>" + product.getQty() + "</td>" + "<td>" + product.getTotal()
	                + "</td>" + "</tr>";
	        jspContent += productInfo;
	        id++;
	    }

	    Double totalAmount = null;
	    Double totalPrice = null;
	    Double discount = null;
	    String charge = null;
	    for (OrderDetails product : order) {
	        totalAmount = product.getOrder().getAmount();
	        totalPrice = product.getOrder().getTotal();
	        discount = product.getOrder().getDiscount();
	        charge = product.getOrder().getCharge();
	    }

	    String totalInfo = "<tr>" +
	            "<td colspan='4' style='text-align: left;'>Total Price</td>" +
	            "<td>&#8377; " + totalAmount + "</td>" +
	            "</tr>" +
	            "<tr>" +
	            "<td colspan='4' style='text-align: left;'>Shipping</td>" +
	            "<td>&#8377; " + charge + "</td>" +
	            "</tr>";

	    if (discount != 0) {
	        totalInfo += "<tr>" +
	                "<td colspan='4' style='text-align: left;'>Discount Price</td>" +
	                "<td>&#8377; " + discount + "</td>" +
	                "</tr>";
	    }

	    totalInfo += "<tr>" +
	            "<td colspan='4' style='text-align: left;'>Total</td>" +
	            "<td>&#8377; " + totalPrice + "</td>" +
	            "</tr>";

	    jspContent += totalInfo;

	    String fullHTML = "</table>" + "</body>\n" + "</html>";
	    jspContent += fullHTML;

	    return jspContent;
	}


}
