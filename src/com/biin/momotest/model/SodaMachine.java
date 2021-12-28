package com.biin.momotest.model;

import java.util.ArrayList;

public class SodaMachine {
	private ArrayList<Product> listProduct;
	private int balance;

	public SodaMachine() {
		super();
		listProduct = new ArrayList<Product>();
		balance = Currency.EMPTY_BALANCE.getDenomination();
		initProductsAsRequirement();
	}

	private SodaMachine(ArrayList<Product> listProduct, int balance) {
		super();
		this.listProduct = listProduct;
		this.balance = balance;
	}

	public ArrayList<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(ArrayList<Product> listProduct) {
		this.listProduct = listProduct;
	}

	public Product getProduct(int indexOfProduct) {
		return listProduct.get(indexOfProduct);
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	// Adding product, follow requirement of this test
	private void initProductsAsRequirement() {
		listProduct.add(new Product("Coke", 50, 10000));
		listProduct.add(new Product("Pepsi", 50, 10000));
		listProduct.add(new Product("Soda", 50, 20000));
	}

	public void inputMoney(Currency inputMoney) {
		balance += inputMoney.getDenomination();
	}

	/*
	 * * This function return current balance and make it to zero (empty the
	 * balance)
	 */

	public int getAndEmptyBalance() {
		int currentBalance = balance;
		balance = 0;
		return currentBalance;
	}
	
	public int getNumberOfTypeProduct() {
		return listProduct.size();
	}

	public void releaseProduct(Product product) {

	}
}
