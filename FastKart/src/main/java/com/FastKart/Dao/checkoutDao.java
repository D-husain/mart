package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.AddressRepository;
import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.Repository.OrderDetailsRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.User;

@Service
public class checkoutDao {

	@Autowired private CheckOutRepository checkOutRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private userDao udao;
	@Autowired private OrderRepository orderrepo;
	@Autowired private OrderRepository orderRepository;
	@Autowired private productDao  productDao;
	@Autowired private OrderDetailsRepository orderDetailsRepository;
	@Autowired private CheckOutRepository checkRepository;
	@Autowired private UserRepository userRepository;

	public CheckOut setShippingAddress(CheckOut checkOut, int addressId, Principal principal) {
        Address address = addressRepository.findById(addressId).orElse(null);
        User user = userRepository.getUserByUserName(principal.getName());

        if (address != null && user != null) {
            if (checkOut.getUser() != null && checkOut.getUser().equals(user.getId())) {
                updateCheckOut(checkOut, address);
                checkOutRepository.save(checkOut);
            } else {
                checkOut.setUser(user);
                updateCheckOut(checkOut, address);
                checkOut.setCreated_at(LocalDateTime.now());
                checkOutRepository.save(checkOut);
            }
        }

        return checkOut;
    }

    private void updateCheckOut(CheckOut checkOut, Address address) {
        checkOut.setAddress(address);
        checkOut.setUpdated_at(LocalDateTime.now());
    }
    
    
    
    public CheckOut saveUserShipping(CheckOut shippingAddress, int addressId, Principal principal) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) {
            return null;
        }

        User user = userRepository.getUserByUserName(principal.getName());
        if (user == null) {
            return null;
        }

        List<CheckOut> existingShippingAddresses = checkOutRepository.findByUserId(user.getId());

        if (!existingShippingAddresses.isEmpty()) {
            if (existingShippingAddresses.size() == 1) {
                CheckOut existingShippingAddress = existingShippingAddresses.get(0);
                existingShippingAddress.setUser(user);
                existingShippingAddress.setAddress(address);
                existingShippingAddress.setUpdated_at(LocalDateTime.now());
                existingShippingAddress.setPaymentoption(shippingAddress.getPaymentoption());
                existingShippingAddress.setDeliveryoption(shippingAddress.getDeliveryoption());

                checkOutRepository.save(existingShippingAddress);
                return existingShippingAddress; 
            }
        }

        shippingAddress.setUser(user);
        shippingAddress.setAddress(address);
        shippingAddress.setCreated_at(LocalDateTime.now());
        
        checkOutRepository.save(shippingAddress);
        return shippingAddress; 
    }

    
    
	public List<CheckOut> showShippingAddress(Principal principal) {
		User loggedInUser = udao.getLoggedInUser(principal);
		return checkOutRepository.findByUser(loggedInUser);
	}

}
