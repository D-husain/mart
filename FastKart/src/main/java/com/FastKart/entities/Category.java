package com.FastKart.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="category")
public class Category {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="cname")
	private String cname;
	@Column(name="cimage")
	private String cimage;
	
	
	 @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	    private List<Product> products;
	


	public Category(int id, String cname, String cimage, List<Product> products) {
		super();
		this.id = id;
		this.cname = cname;
		this.cimage = cimage;
		this.products = products;
	}



	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCimage() {
		return cimage;
	}

	public void setCimage(String cimage) {
		this.cimage = cimage;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}



	@Override
	public String toString() {
		return "Category [id=" + id + ", cname=" + cname + ", cimage=" + cimage + ", products=" + products + "]";
	}

	
	
	
}
