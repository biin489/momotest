package com.biin.momotest.model;

public class Product {
	private static final int INIT_ID = 0;
	private static final String INIT_NAME = "";
	private static final int INIT_QUANTITY = 0;
	
	private int id;
	private String name;
	private int quantity;
	private int price;
	
	
	private Product() {
		super();
		this.id = INIT_ID;
		this.name = INIT_NAME;
		this.quantity = INIT_QUANTITY;
		this.price = 0;
	}


	protected Product(int id, String name, int quantity, int price) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	protected Product(String name, int quantity, int price) {
		super();
		this.id += 1;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}


	private void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public void reduceProductQuantity () {
		this.quantity -= 1;
	}
	
	public boolean isInStock() {
		return quantity > 0 ? true : false;
	}
	
	@Override
	public String toString() {
		return "Product name: " + this.name + "\nProduct price: " + this.price + " VND";
	}
}
