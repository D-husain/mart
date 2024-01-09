package com.FastKart.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pname;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_image_id")
	private ProductImg product_image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id")
	private subCategory subcategory;
	
	private int price;
	private int discount_price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_weight_id")
	private ProductWeight Weight;

	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_details_id")
	private ProductDetails details;

	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private LocalDateTime expiry_at;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int id, String pname, ProductImg product_image, Category category, subCategory subcategory,
			int price, int discount_price, ProductWeight weight, String description, ProductDetails details,
			LocalDateTime created_at, LocalDateTime updated_at, LocalDateTime expiry_at) {
		super();
		this.id = id;
		this.pname = pname;
		this.product_image = product_image;
		this.category = category;
		this.subcategory = subcategory;
		this.price = price;
		this.discount_price = discount_price;
		Weight = weight;
		this.description = description;
		this.details = details;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.expiry_at = expiry_at;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", pname=" + pname + ", product_image=" + product_image + ", category=" + category
				+ ", subcategory=" + subcategory + ", price=" + price + ", discount_price=" + discount_price
				+ ", Weight=" + Weight + ", description=" + description + ", details=" + details + ", created_at="
				+ created_at + ", updated_at=" + updated_at + ", expiry_at=" + expiry_at + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public ProductImg getProduct_image() {
		return product_image;
	}
	public void setProduct_image(ProductImg product_image) {
		this.product_image = product_image;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public subCategory getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(subCategory subcategory) {
		this.subcategory = subcategory;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(int discount_price) {
		this.discount_price = discount_price;
	}
	public ProductWeight getWeight() {
		return Weight;
	}
	public void setWeight(ProductWeight weight) {
		Weight = weight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProductDetails getDetails() {
		return details;
	}
	public void setDetails(ProductDetails details) {
		this.details = details;
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
	public LocalDateTime getExpiry_at() {
		return expiry_at;
	}
	public void setExpiry_at(LocalDateTime expiry_at) {
		this.expiry_at = expiry_at;
	}
}
