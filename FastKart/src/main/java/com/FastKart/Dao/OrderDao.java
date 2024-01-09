package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.Repository.OrderDetailsRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;

@Service
public class OrderDao {
	
	@Autowired private OrderRepository orderRepository;
	@Autowired private productDao  productDao;
	@Autowired private OrderDetailsRepository orderDetailsRepository;
	@Autowired private CheckOutRepository checkRepository;
	@Autowired private UserRepository userRepository;
	
	
	public Orders SaveOrder(List<Integer> productIds,int shippingId, Principal principal) {
		CheckOut checkOut = checkRepository.findById(shippingId).orElse(null);
		User user = userRepository.getUserByUserName(principal.getName());
		
        Orders order = new Orders();
        order.setCheckOut(checkOut);
        order.setUser(user);
        order.setOrderdate(LocalDate.now());
        order.setOrdertime(LocalTime.now());
        order.setStatus(1);
        
        order = orderRepository.save(order);
        
        for (Integer productId : productIds) {
            Product product = productDao.findProductById(productId);
            OrderDetails orderItem = new OrderDetails();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderDetailsRepository.save(orderItem);
        }
        return order;
    }
}
