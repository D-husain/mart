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
@Table(name = "checkout")
public class CheckOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	private String deliveryoption;
	private String paymentoption;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getDeliveryoption() {
		return deliveryoption;
	}
	public void setDeliveryoption(String deliveryoption) {
		this.deliveryoption = deliveryoption;
	}
	public String getPaymentoption() {
		return paymentoption;
	}
	public void setPaymentoption(String paymentoption) {
		this.paymentoption = paymentoption;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	@Override
	public String toString() {
		return "CheckOut [id=" + id + ", address=" + address + ", deliveryoption=" + deliveryoption + ", paymentoption="
				+ paymentoption + ", user=" + user + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	public CheckOut(int id, Address address, String deliveryoption, String paymentoption, User user,
			LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.id = id;
		this.address = address;
		this.deliveryoption = deliveryoption;
		this.paymentoption = paymentoption;
		this.user = user;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public CheckOut() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
