package com.FastKart.Dto;

import com.FastKart.entities.Cart;

public class AddToCartRequest {

	 private Cart cart;
	    private int productId;
	    private int quantity;
		public Cart getCart() {
			return cart;
		}
		public void setCart(Cart cart) {
			this.cart = cart;
		}
		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	    
}
