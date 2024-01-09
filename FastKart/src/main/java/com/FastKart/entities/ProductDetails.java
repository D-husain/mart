package com.FastKart.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_details")
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	private String Store_information;
	private String type;
	private String SKU;
	private LocalDate MFG;
	private String stock;
	private String Organic;
	private String Manufacturer;
	private String Specialty;
	private String IngredientType;
	private String brand;
	private String form;
	private String PackageInformation;
	private String Manufacturerfrom;
	private int NetQuantity;
	private int soldqty;
	private int totalqty;
	private String careInstuctions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getStore_information() {
		return Store_information;
	}
	public void setStore_information(String store_information) {
		Store_information = store_information;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public LocalDate getMFG() {
		return MFG;
	}
	public void setMFG(LocalDate mFG) {
		MFG = mFG;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getOrganic() {
		return Organic;
	}
	public void setOrganic(String organic) {
		Organic = organic;
	}
	public String getManufacturer() {
		return Manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}
	public String getSpecialty() {
		return Specialty;
	}
	public void setSpecialty(String specialty) {
		Specialty = specialty;
	}
	public String getIngredientType() {
		return IngredientType;
	}
	public void setIngredientType(String ingredientType) {
		IngredientType = ingredientType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getPackageInformation() {
		return PackageInformation;
	}
	public void setPackageInformation(String packageInformation) {
		PackageInformation = packageInformation;
	}
	public String getManufacturerfrom() {
		return Manufacturerfrom;
	}
	public void setManufacturerfrom(String manufacturerfrom) {
		Manufacturerfrom = manufacturerfrom;
	}
	public int getNetQuantity() {
		return NetQuantity;
	}
	public void setNetQuantity(int netQuantity) {
		NetQuantity = netQuantity;
	}
	public int getSoldqty() {
		return soldqty;
	}
	public void setSoldqty(int soldqty) {
		this.soldqty = soldqty;
	}
	public int getTotalqty() {
		return totalqty;
	}
	public void setTotalqty(int totalqty) {
		this.totalqty = totalqty;
	}
	public String getCareInstuctions() {
		return careInstuctions;
	}
	public void setCareInstuctions(String careInstuctions) {
		this.careInstuctions = careInstuctions;
	}
	@Override
	public String toString() {
		return "ProductDetails [id=" + id + ", product=" + product + ", Store_information=" + Store_information
				+ ", type=" + type + ", SKU=" + SKU + ", MFG=" + MFG + ", stock=" + stock + ", Organic=" + Organic
				+ ", Manufacturer=" + Manufacturer + ", Specialty=" + Specialty + ", IngredientType=" + IngredientType
				+ ", brand=" + brand + ", form=" + form + ", PackageInformation=" + PackageInformation
				+ ", Manufacturerfrom=" + Manufacturerfrom + ", NetQuantity=" + NetQuantity + ", soldqty=" + soldqty
				+ ", totalqty=" + totalqty + ", careInstuctions=" + careInstuctions + "]";
	}
	public ProductDetails(int id, Product product, String store_information, String type, String sKU, LocalDate mFG,
			String stock, String organic, String manufacturer, String specialty, String ingredientType, String brand,
			String form, String packageInformation, String manufacturerfrom, int netQuantity, int soldqty, int totalqty,
			String careInstuctions) {
		super();
		this.id = id;
		this.product = product;
		Store_information = store_information;
		this.type = type;
		SKU = sKU;
		MFG = mFG;
		this.stock = stock;
		Organic = organic;
		Manufacturer = manufacturer;
		Specialty = specialty;
		IngredientType = ingredientType;
		this.brand = brand;
		this.form = form;
		PackageInformation = packageInformation;
		Manufacturerfrom = manufacturerfrom;
		NetQuantity = netQuantity;
		this.soldqty = soldqty;
		this.totalqty = totalqty;
		this.careInstuctions = careInstuctions;
	}
	public ProductDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
