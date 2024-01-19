package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Address;
import com.FastKart.entities.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {
 
	List<Address> findByUser(User user);
	
	@Query("SELECT ad FROM Address ad WHERE ad.isDefault = true AND ad.user = :loggedInUser")
	List<Address> findDefaultAddress(@Param("loggedInUser") User loggedInUser);

}
