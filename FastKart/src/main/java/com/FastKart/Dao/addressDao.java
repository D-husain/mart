package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.AddressRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.User;

@Service
public class addressDao {

	@Autowired private AddressRepository addressRepository;
	@Autowired private userDao udao;

	public Address addAddress(Address address, Principal principal) {
		User loggedInUser = udao.getLoggedInUser(principal);
		if (loggedInUser != null) {
			address.setUser(loggedInUser);
			address.setAddress(address.getAddress().toString());
			address.setCreated_at(LocalDateTime.now());
			return addressRepository.save(address);
		} else {
			return null;
		}
	}

	public List<Address> showAllAddress(Principal principal) {
		User loggedInUser = udao.getLoggedInUser(principal);
		return addressRepository.findByUser(loggedInUser);
	}
	
	public List<Address> getDefaultShippingAddress(Principal principal) {
		User loggedInUser = udao.getLoggedInUser(principal);
		return addressRepository.findDefaultAddress(loggedInUser);
	}

	public Address getAddressById(int id) {
		Address addressById = addressRepository.findById(id).get();
		return addressById;
	}


	public void deleteAddress(int id) {
		addressRepository.deleteById(id);
	}
}
