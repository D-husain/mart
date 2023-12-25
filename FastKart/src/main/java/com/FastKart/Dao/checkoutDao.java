package com.FastKart.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CheckOutRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.CheckOut;


@Service
public class checkoutDao {

	@Autowired
	private CheckOutRepository checkOutRepository;
	
	public void saveSelectedAddress(Address selectedAddress) {
		
		CheckOut checkOut = new CheckOut();
		checkOut.setAddress(selectedAddress);
		
		checkOutRepository.save(checkOut);
		
	}
}
