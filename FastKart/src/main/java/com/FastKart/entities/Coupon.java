package com.FastKart.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String coupon_name;
	private String coupon_code;
	private int discount;
	private int max_coupon_value;
	private LocalDateTime expiry_date;
	
	
	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Coupon(int id, String coupon_name, String coupon_code, int discount, int max_coupon_value,
			LocalDateTime expiry_date) {
		super();
		this.id = id;
		this.coupon_name = coupon_name;
		this.coupon_code = coupon_code;
		this.discount = discount;
		this.max_coupon_value = max_coupon_value;
		this.expiry_date = expiry_date;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", coupon_name=" + coupon_name + ", coupon_code=" + coupon_code + ", discount="
				+ discount + ", max_coupon_value=" + max_coupon_value + ", expiry_date=" + expiry_date + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getMax_coupon_value() {
		return max_coupon_value;
	}
	public void setMax_coupon_value(int max_coupon_value) {
		this.max_coupon_value = max_coupon_value;
	}
	public LocalDateTime getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(LocalDateTime expiry_date) {
		this.expiry_date = expiry_date;
	}
}