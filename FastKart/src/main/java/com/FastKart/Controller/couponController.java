package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.FastKart.Dao.couponDao;
import com.FastKart.entities.Coupon;

import ch.qos.logback.core.model.Model;

@Controller
public class couponController {
	
	@Autowired
	private couponDao coupondao;
	
	@PostMapping("/addCoupon")
	public String addCoupn(@ModelAttribute Coupon coupon, Model m) {
		
		Coupon c = coupondao.addCoupon(coupon);
		
		return "redirect:/allCoupon";
		
	}

}
