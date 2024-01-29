package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.Repository.OrderDetailsRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;
import com.FastKart.entities.User;

@Service
public class OrderDao {

	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderDetailsRepository orderDetailsRepository;
	@Autowired private CheckOutRepository checkRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CartRepository cartRepository;
	@Autowired private userDao udao;

	

	public Orders saveUserOrders(Orders order, int shippingId, Principal principal) {
	    CheckOut checkOut = checkRepository.findById(shippingId).orElse(null);
	    User user = userRepository.getUserByUserName(principal.getName());

	    order.setCheckOut(checkOut);
	    order.setUser(user);
	    order.setOrderdate(LocalDate.now());
	    order.setOrdertime(LocalTime.now());
	    order.setStatus(1);

	    orderRepository.save(order);
	    Integer userId = user.getId();
	    cartRepository.deleteByCartuid(userId);
	    
		return order;
	}

	
	public List<Orders> showUsersOrder(Principal principal) {
		User loggedInUser = udao.getLoggedInUser(principal);
		return orderRepository.findByUser(loggedInUser);
	}
	
	public Orders findOrdersById(int id) {
		Orders orderById = orderRepository.findById(id).get();
		return orderById;
	}
	
	 public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
    	 List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderid(orderId);
		return orderDetailsList;
    }
    
    public Map<Integer, List<OrderDetails>> getOrderDetailsMap(List<Orders> orders) {
        Map<Integer, List<OrderDetails>> orderDetailsMap = new HashMap<>();
        for (Orders order : orders) {
            List<OrderDetails> orderDetails = getOrderDetailsByOrderId(order.getId());
            orderDetailsMap.put(order.getId(), orderDetails);
        }
        return orderDetailsMap;
    }


	public List<Orders> ShowUserOrders() {
		return orderRepository.findAll();
	}


	public List<Orders> viewOrders(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    

}
