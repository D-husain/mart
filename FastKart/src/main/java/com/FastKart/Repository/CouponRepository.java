package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Coupon;
import com.FastKart.entities.User;
import com.FastKart.entities.UserCoupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer > {

	 @Query("SELECT c FROM UserCoupon c WHERE c.user = :user")
	    UserCoupon findUserCouponByUser(@Param("user") User user);
	 
	 @Query("SELECT cc FROM Coupon cc WHERE cc.coupon_code = :couponCode")
	    Coupon findByCouponCode(String couponCode);

	
	
}
