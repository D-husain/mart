package com.FastKart.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cname;
	private String cimage;
	private String cimage_icon;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Product> products;

	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int id, String cname, String cimage, String cimage_icon, List<Product> products,
			LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.id = id;
		this.cname = cname;
		this.cimage = cimage;
		this.cimage_icon = cimage_icon;
		this.products = products;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", cname=" + cname + ", cimage=" + cimage + ", cimage_icon=" + cimage_icon
				+ ", products=" + products + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
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
	public String getCimage_icon() {
		return cimage_icon;
	}
	public void setCimage_icon(String cimage_icon) {
		this.cimage_icon = cimage_icon;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
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
}
