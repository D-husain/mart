package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.CheckOut;
import com.FastKart.entities.User;

public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

	List<CheckOut> findByUserId(int id);

	List<CheckOut> findByUser(User loggedInUser);

}
