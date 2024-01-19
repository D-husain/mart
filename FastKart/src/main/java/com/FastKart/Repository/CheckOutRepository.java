package com.FastKart.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.CheckOut;
import com.FastKart.entities.User;

public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

	List<CheckOut> findByUserId(int id);

	List<CheckOut> findByUser(User loggedInUser);

	@Query("SELECT co FROM CheckOut co WHERE co.user = :loggedInUser AND co.created_at > :creationTimestamp")
    List<CheckOut> findCheckOutsByUserAndCreatedAtAfter(
        @Param("loggedInUser") User loggedInUser, 
        @Param("creationTimestamp") LocalDateTime cutoffTime
    );

}
