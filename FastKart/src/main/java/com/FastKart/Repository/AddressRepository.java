package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.Address;
import com.FastKart.entities.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {
 
	List<Address> findByUser(User user);
}
