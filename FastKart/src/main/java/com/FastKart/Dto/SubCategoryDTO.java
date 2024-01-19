package com.FastKart.Dto;

public class SubCategoryDTO {

	private int id;
	private String subcname;
	private String subcimg;
	private String subcicon;
	private int cid;
	private String cname;
	
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
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public SubCategoryDTO(int id, String subcname, String subcimg, String subcicon, int cid, String cname) {
		super();
		this.id = id;
		this.subcname = subcname;
		this.subcimg = subcimg;
		this.subcicon = subcicon;
		this.cid = cid;
		this.cname = cname;
	}
	
	
	
}
