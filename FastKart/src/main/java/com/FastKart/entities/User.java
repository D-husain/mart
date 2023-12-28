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
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "User name cannot be null!!")
	@Size(min = 3, max = 10, message = "User name must be between 3-10 characters!!")
	private String name;

	@NotBlank(message = "Email is necessary")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.")
	private String email;

	@NotBlank(message = "Password is mandatory")
	//@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.")
	private String password;

	private String role;

	@AssertTrue(message = "Checkbox must be checked")
	private boolean checkbox;

	private LocalDateTime created_at;
	private LocalDateTime updated_at;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<WishList> wishlist;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Cart> cart;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<WishList> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<WishList> wishlist) {
		this.wishlist = wishlist;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", checkbox=" + checkbox + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", addresses=" + addresses + ", wishlist=" + wishlist + ", cart=" + cart + "]";
	}

	public User(int id,
			@NotBlank(message = "User name cannot be null!!") @Size(min = 3, max = 10, message = "User name must be between 3-10 characters!!") String name,
			@NotBlank(message = "Email is necessary") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.") String email,
			@NotBlank(message = "Password is mandatory") String password, String role,
			@AssertTrue(message = "Checkbox must be checked") boolean checkbox, LocalDateTime created_at,
			LocalDateTime updated_at, List<Address> addresses, List<WishList> wishlist, List<Cart> cart) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.checkbox = checkbox;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.addresses = addresses;
		this.wishlist = wishlist;
		this.cart = cart;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
