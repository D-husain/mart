package com.FastKart.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CouponRepository;
import com.FastKart.entities.Coupon;

@Service
public class couponDao {
	
	@Autowired private CouponRepository couponRepository;
	
	
	public Coupon addCoupon(Coupon coupon) {
		Coupon c = couponRepository.save(coupon);
		return c;
	}
	
	public List<Coupon> allCoupon(){
		List<Coupon> allCoupon = couponRepository.findAll();
		return allCoupon;
	}

	
	public void AddCoupon(Coupon coupons) {
		couponRepository.save(coupons);
	}
	
	public List<Coupon> ShowCoupon() {
		return this.couponRepository.findAll();
	}
	
	public void DeleteCoupon(int id) {
		couponRepository.deleteById(id);
	}
}
