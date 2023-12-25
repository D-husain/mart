package com.FastKart.entities;

import org.hibernate.annotations.Parent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {

	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="User name can not be null !!")
	@Size(min=3, max=10, message="User name must be between 3-12 characters !!")
	private String name;
	
	@NotBlank(message="Email is neccessary")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.")
	private String email;
	
	@NotBlank(message="Password is menatory")
	/*
	 * @Pattern(regexp =
	 * "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$",
	 * message="Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character."
	 * )
	 */
	private String password;
	private String role;
	
	@AssertTrue
	private boolean checkbox;
	
	
	
	public User(int id,
			@NotBlank(message = "User name can not be null !!") @Size(min = 3, max = 10, message = "User name must be between 3-12 characters !!") String name,
			@NotBlank(message = "Email is neccessary") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.") String email,
			@NotBlank(message = "Password is menatory") String password, String role, boolean checkbox) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.checkbox = checkbox;
	}



	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
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




	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", checkbox=" + checkbox + "]";
	}





	

	
	
}
