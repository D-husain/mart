package com.FastKart.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="usercoupon")
public class UserCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String coupon;
	private String discount;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime expiry_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(LocalDateTime expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public String toString() {
		return "UserCoupon [id=" + id + ", coupon=" + coupon + ", discount=" + discount + ", user=" + user
				+ ", expiry_date=" + expiry_date + "]";
	}

	public UserCoupon(int id, String coupon, String discount, User user, LocalDateTime expiry_date) {
		super();
		this.id = id;
		this.coupon = coupon;
		this.discount = discount;
		this.user = user;
		this.expiry_date = expiry_date;
	}

	public UserCoupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
