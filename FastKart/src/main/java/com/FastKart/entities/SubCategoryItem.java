package com.FastKart.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name= "subcategoryitem")
public class SubCategoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String subitemname;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subcategory_id")
	private subCategory subCat;
	
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubitemname() {
		return subitemname;
	}
	public void setSubitemname(String subitemname) {
		this.subitemname = subitemname;
	}
	public subCategory getSubCat() {
		return subCat;
	}
	public void setSubCat(subCategory subCat) {
		this.subCat = subCat;
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
	@Override
	public String toString() {
		return "SubCategoryItem [id=" + id + ", subitemname=" + subitemname + ", subCat=" + subCat + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
	public SubCategoryItem(int id, String subitemname, subCategory subCat, LocalDateTime created_at,
			LocalDateTime updated_at) {
		super();
		this.id = id;
		this.subitemname = subitemname;
		this.subCat = subCat;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public SubCategoryItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
