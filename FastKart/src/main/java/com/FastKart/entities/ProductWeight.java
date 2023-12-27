package com.FastKart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_weight")
public class ProductWeight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String kg;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public ProductWeight() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductWeight(int id, String kg, Product product) {
		super();
		this.id = id;
		this.kg = kg;
		this.product = product;
	}
	@Override
	public String toString() {
		return "ProductWeight [id=" + id + ", kg=" + kg + ", product=" + product + ", getId()=" + getId() + ", getKg()="
				+ getKg() + ", getProduct()=" + getProduct() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKg() {
		return kg;
	}
	public void setKg(String kg) {
		this.kg = kg;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
