package com.FastKart.APIController;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.FastKart.Dao.couponDao;
import com.FastKart.entities.Coupon;


@Controller
public class CouponApiController {
	
	@Autowired private couponDao couponDao;

	@PostMapping("/coupon/save")
	public ResponseEntity<Void> saveOrUpdatecoupon(@ModelAttribute("Coupon") Coupon coupons) {
		coupons.setExpiry_date(LocalDateTime.now().minusDays(10));
		couponDao.AddCoupon(coupons);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/coupon/data")
	public ResponseEntity<List<Coupon>> getcouponList() {
		return new ResponseEntity<List<Coupon>>(couponDao.ShowCoupon(), HttpStatus.OK);
	}
	
	@DeleteMapping("/coupon/delete/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Integer id) {
		couponDao.DeleteCoupon(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
