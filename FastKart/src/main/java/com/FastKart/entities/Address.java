package com.FastKart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="address")
public class Address {
	
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@JoinColumn(name="firstName")
	private String firstName;
	@JoinColumn(name="lastName")
	private String lastName;
	private String email;
	private long number;
	@JoinColumn(name="addressText")
	private String addressText;
	private String state;
	private String country;
	@JoinColumn(name="pinCode")
	private int pinCode;
	
	 @ManyToOne
     @JoinColumn(name="uid")
	private User user;
	
	 
	 
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(int id, String firstName, String lastName, String email, long number, String addressText,
			String state, String country, int pinCode, User user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.number = number;
		this.addressText = addressText;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	@Override
	public String toString() {
		return "Address [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", number=" + number + ", addressText=" + addressText + ", state=" + state + ", country=" + country
				+ ", pinCode=" + pinCode + ", user=" + user + "]";
	}

  
}
