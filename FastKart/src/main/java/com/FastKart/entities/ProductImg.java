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
@Table(name = "product_image")
public class ProductImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductImg [id=" + id + ", image1=" + image1 + ", image2=" + image2 + ", image3=" + image3 + ", image4="
				+ image4 + ", product=" + product + "]";
	}

	public ProductImg(int id, String image1, String image2, String image3, String image4, Product product) {
		super();
		this.id = id;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.image4 = image4;
		this.product = product;
	}

	public ProductImg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
