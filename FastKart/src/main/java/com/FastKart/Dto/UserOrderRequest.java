package com.FastKart.Dto;

import java.util.List;

import com.FastKart.entities.OrderDetails;
import com.FastKart.entities.Orders;

public class UserOrderRequest {

	 private Orders order;
	    private List<OrderDetails> orderDetailsList;
		public Orders getOrder() {
			return order;
		}
		public void setOrder(Orders order) {
			this.order = order;
		}
		public List<OrderDetails> getOrderDetailsList() {
			return orderDetailsList;
		}
		public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
			this.orderDetailsList = orderDetailsList;
		}
		@Override
		public String toString() {
			return "UserOrderRequest [order=" + order + ", orderDetailsList=" + orderDetailsList + "]";
		}
		public UserOrderRequest(Orders order, List<OrderDetails> orderDetailsList) {
			super();
			this.order = order;
			this.orderDetailsList = orderDetailsList;
		}
		public UserOrderRequest() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
}
