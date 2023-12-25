package com.FastKart.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String coupon_name;
	private String coupon_code;
	private int discount;
	private int min_cart_subtotal;
	private int max_cart_subtotal;
    private String expiry_date;
    
    
    
    
	public Coupon(int id, String coupon_name, String coupon_code, int discount, int min_cart_subtotal, int max_cart_subtotal,
			String expiry_date) {
		super();
		this.id = id;
		this.coupon_name = coupon_name;
		this.coupon_code = coupon_code;
		this.discount = discount;
		this.min_cart_subtotal = min_cart_subtotal;
		this.max_cart_subtotal = max_cart_subtotal;
		this.expiry_date = expiry_date;
	}
	
	
	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getMin_cart_subtotal() {
		return min_cart_subtotal;
	}
	public void setMin_cart_subtotal(int min_cart_subtotal) {
		this.min_cart_subtotal = min_cart_subtotal;
	}
	public int getMax_cart_subtotal() {
		return max_cart_subtotal;
	}
	public void setMax_cart_subtotal(int max_cart_subtotal) {
		this.max_cart_subtotal = max_cart_subtotal;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
    
    
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", coupon_name=" + coupon_name + ", coupon_code=" + coupon_code + ", discount="
				+ discount + ", min_cart_subtotal=" + min_cart_subtotal + ", max_cart_subtotal=" + max_cart_subtotal
				+ ", expiry_date=" + expiry_date + "]";
	}

    
    
	
}
