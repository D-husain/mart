package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CouponRepository;
import com.FastKart.Repository.UserCouponRepository;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.User;
import com.FastKart.entities.UserCoupon;

@Service
public class UserCouponDao {

	@Autowired private userDao udao;
	@Autowired private UserCouponRepository ucouponRepository;
	@Autowired private CouponRepository couponRepository;

	public UserCoupon addUserCoupon(UserCoupon userCoupon, Principal principal) {
	    User loggedInUser = udao.getLoggedInUser(principal);
	    if (loggedInUser != null) {
	        if (isValidCoupon(userCoupon.getCoupon())) {
	            userCoupon.setUser(loggedInUser);
	            userCoupon.setExpiry_date(LocalDateTime.now().plusHours(1));
	            return ucouponRepository.save(userCoupon);
	        } else {
	            return null;
	        }
	    } else {
	        return null;
	    }
	}
	
	private boolean isValidCoupon(String couponCode) {
		List<Coupon> allCoupons = couponRepository.findAll();
		return allCoupons.stream().anyMatch(coupon -> coupon.getCoupon_code().equals(couponCode));
	}
	
	
}
