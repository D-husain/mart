package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer > {

	
	
}
