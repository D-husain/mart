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
@Table(name = "subcategory")
public class subCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String subcname;
	private String subcimg;
	private String subcicon;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	public subCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public subCategory(int id, String subcname, String subcimg, String subcicon, Category category,
			LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.id = id;
		this.subcname = subcname;
		this.subcimg = subcimg;
		this.subcicon = subcicon;
		this.category = category;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubcname() {
		return subcname;
	}
	public void setSubcname(String subcname) {
		this.subcname = subcname;
	}
	public String getSubcimg() {
		return subcimg;
	}
	public void setSubcimg(String subcimg) {
		this.subcimg = subcimg;
	}
	public String getSubcicon() {
		return subcicon;
	}
	public void setSubcicon(String subcicon) {
		this.subcicon = subcicon;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
