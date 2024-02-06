package com.FastKart.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double amount;
	private String cancelorder;
	private String charge;
	private double discount;
	private LocalDate orderdate;
	private LocalTime ordertime;
	private int status;
	private double total;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "shipping_id")
	private CheckOut checkOut;
	
	@OneToMany(mappedBy = "order")
    private List<OrderDetails> orderList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<OrderDetails> getOrderList() {
		return orderList;
	}
	
	public void setOrderList(List<OrderDetails> orderList) {
		this.orderList = orderList;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCancelorder() {
		return cancelorder;
	}

	public void setCancelorder(String cancelorder) {
		this.cancelorder = cancelorder;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public LocalDate getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(LocalDate localDate) {
		this.orderdate = localDate;
	}

	public LocalTime getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(LocalTime localTime) {
		this.ordertime = localTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CheckOut getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(CheckOut checkOut) {
		this.checkOut = checkOut;
	}


	@Override
	public String toString() {
		return "Orders [id=" + id + ", amount=" + amount + ", cancelorder=" + cancelorder + ", charge=" + charge
				+ ", discount=" + discount + ", orderdate=" + orderdate + ", ordertime=" + ordertime + ", status="
				+ status + ", total=" + total + ", user=" + user + ", checkOut=" + checkOut + "]";
	}

	public Orders(int id, double amount, String cancelorder, String charge, double discount, LocalDate orderdate,
			LocalTime ordertime, int status, double total, User user, CheckOut checkOut,
			List<OrderDetails> orderdetails) {
		super();
		this.id = id;
		this.amount = amount;
		this.cancelorder = cancelorder;
		this.charge = charge;
		this.discount = discount;
		this.orderdate = orderdate;
		this.ordertime = ordertime;
		this.status = status;
		this.total = total;
		this.user = user;
		this.checkOut = checkOut;
	}

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
