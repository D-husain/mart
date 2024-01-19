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
@Table(name = "user_address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String name;
	private String contact;
	private String address;
	private String type;
	private String country;
	private String city;
	private String state;
	private int pinCode;
	private boolean isDefault;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
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


	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", user=" + user + ", name=" + name + ", contact=" + contact + ", address="
				+ address + ", type=" + type + ", country=" + country + ", city=" + city + ", state=" + state
				+ ", pinCode=" + pinCode + ", isDefault=" + isDefault + ", created_at=" + created_at + ", updated_at="
				+ updated_at + "]";
	}

	public Address(int id, User user, String name, String contact, String address, String type, String country,
			String city, String state, int pinCode, boolean isDefault, LocalDateTime created_at,
			LocalDateTime updated_at) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.type = type;
		this.country = country;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
		this.isDefault = isDefault;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
