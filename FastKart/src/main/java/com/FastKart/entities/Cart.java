package com.FastKart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int quntity;
	private int total;
	private int shipping;
	private int grand_total;
	
	
	 @ManyToOne
	 @JoinColumn(name="pid")
	private Product product;
	 
	 @ManyToOne
	 @JoinColumn(name="uid")
	private User user;
	
	 

//	 
		public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}



		public Cart(int id, int quntity, int total, int shipping, int grand_total, Product product, User user) {
	super();
	this.id = id;
	this.quntity = quntity;
	this.total = total;
	this.shipping = shipping;
	this.grand_total = grand_total;
	this.product = product;
	this.user = user;
}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		

		public int getQuntity() {
			return quntity;
		}

		public void setQuntity(int quntity) {
			this.quntity = quntity;
		}

		public int getTotal() {
			return total;
		}

		
		public void setTotal(int total) {
			this.total = total;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		
		public int getShipping() {
			return shipping;
		}




		public void setShipping(int shipping) {
			this.shipping = shipping;
		}




		public int getGrand_total() {
			return grand_total;
		}



		public void setGrand_total(int grand_total) {
			this.grand_total = grand_total;
		}


		@Override
		public String toString() {
			return "Cart [id=" + id + ", quntity=" + quntity + ", total=" + total + ", shipping=" + shipping
					+ ", grand_total=" + grand_total + ", product=" + product + ", user=" + user + "]";
		}

		
}
