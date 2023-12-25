package com.FastKart.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="checkout")
public class CheckOut {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "aid")
	private Address address;

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

	public CheckOut(int id, Address address) {
		super();
		this.id = id;
		this.address = address;
	}

	public CheckOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CheckOut [id=" + id + ", address=" + address + "]";
	}
	
	
}
