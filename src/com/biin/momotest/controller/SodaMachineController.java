package com.biin.momotest.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.biin.momotest.model.Currency;
import com.biin.momotest.model.Product;
import com.biin.momotest.model.SodaMachine;
import com.biin.momotest.view.View;

public class SodaMachineController {
	public static final int MAX_TYPE_OF_CURRENCY = 5;
	public static final int MAX_TYPE_OF_CONTINUES_OPTION = 2;

	enum UserState {
		ADDING_MONEY, SELECT_PRODUCT, CANCEL_OR_END_REQUEST;
	}

	SodaMachine sodaMachine;
	UserState userState;
	View view;
	boolean forceChangeState;

	public SodaMachineController(View view, SodaMachine sodaMachine) {
		view.setController(this);
		this.view = view;
		this.sodaMachine = sodaMachine;
		userState = UserState.ADDING_MONEY;
		forceChangeState = false;
	}

	/*
	 * This function is core of the class. It will detect and bring user to correct
	 * state.
	 * 
	 * ADDING_MONEY is for adding money state, it will ask user to insert money to
	 * continues select product.
	 * 
	 * SELECT_PRODUCT is for selecting product on the machine. if balance enough to
	 * pay, product will be released.
	 * 
	 * CANCEL_OR_END_REQUEST is the state which will return back money to user and
	 * cancel current process.
	 * 
	 */
	public void run() {
		while (true) {
			switch (userState) {
			case ADDING_MONEY:
				view.clearSodaMachineScreen();
				view.showSodaMachineScreen();
				view.askUserForAddingMoney();
				if (isJustForceChangeState()) {
					changeStateImmediately();
					break;
				}
				view.askAddingMoneyOrContinues();
				break;
			case SELECT_PRODUCT:
				view.clearSodaMachineScreen();
				view.showSodaMachineScreen();
				view.askUserForSelectingProduct();
				break;
			case CANCEL_OR_END_REQUEST:
				view.clearSodaMachineScreen();
				processCancelEndRequest();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + userState);
			}
		}
	}

	// Return current balance on machine
	public int getBalance() {
		return sodaMachine.getBalance();
	}

	// Show all product on the machine with Name, Quantity and price
	public void showListProduct() {
		ArrayList<Product> listProduct = sodaMachine.getListProduct();
		for (Product product : listProduct) {
			view.showAProduct(listProduct.indexOf(product), product.getName(), product.getQuantity(),
					product.getPrice());
		}
	}

	// Adding money to balance of machine
	public void addingMoneyByUser(int inputMoney) {
		for (Currency inputCurrency : Currency.values()) {
			if (inputCurrency.getDenomination() == inputMoney) {
				sodaMachine.inputMoney(inputCurrency);
			}
		}
	}

	// Release product to user
	public void releaseProduct(Product product) {
		String releaseProductShowOut = product.toString();
		view.showReturnedProduct(releaseProductShowOut);
	}

	// Get product by the index from view input
	public Product getProduct(int indexOfProduct) {
		return sodaMachine.getProduct(indexOfProduct);
	}

	/*
	 * This function is for selecting product. When a product is selected, it will
	 * check if product is in stock or not, then check balance if it enough to pay,
	 * finally will release product to use.
	 * 
	 * If do not meet any upper requirement, it will make a notice in screen.
	 */
	public void selectProduct(Product product) {
		if (product.isInStock()) {
			if (isMeetTheBalance(product)) {
				purchaseProduct(product);
				product.reduceProductQuantity();
				releaseProduct(product);
			} else {
				view.warningNotEnoughMoney();
				changeToAddingMoneyState();
			}
		} else {
			view.productIsOutOfStock();
		}
	}

	// Check if enough money then pay the product
	private void purchaseProduct(Product product) {
		int currentBalance = sodaMachine.getBalance();
		currentBalance -= product.getPrice();
		sodaMachine.setBalance(currentBalance);
	}

	// Function for End request or Cancel request. Return the money (if has) for user
	public void processCancelEndRequest() {
		int returnMoney = sodaMachine.getAndEmptyBalance();
		view.returnMoneyEndProcess(returnMoney);
		changeToAddingMoneyState();
		changeStateImmediately();
	}

	// Below are compare functions. Function name is stand for it works.

	public boolean isMeetTheBalance(Product product) {
		return this.sodaMachine.getBalance() >= product.getPrice() ? true : false;
	}

	public boolean isBalanceNotEmpty() {
		return sodaMachine.getBalance() > 0 ? true : false;
	}

	public boolean isLegalProductSelected(byte indexSelectProductOption) {
		return indexSelectProductOption > 0 && indexSelectProductOption < sodaMachine.getNumberOfTypeProduct() ? true
				: false;
	}

	public boolean isLegalDenominationSelected(byte indexSelectProductOption) {
		return indexSelectProductOption > 0 && indexSelectProductOption <= MAX_TYPE_OF_CURRENCY ? true : false;
	}

	public boolean isLegalContinuesSelected(int indexSelectContinuesOption) {
		return indexSelectContinuesOption > 0 && indexSelectContinuesOption <= MAX_TYPE_OF_CONTINUES_OPTION ? true
				: false;
	}

	public boolean isCancelEndRequest(byte indexSelectProductOption) {
		return indexSelectProductOption == 0 ? true : false;
	}

	public boolean isContinuesCurrentState(int indexSelectOption) {
		return indexSelectOption == 1 ? true : false;
	}

	public boolean isGotoNextState(int indexSelectContinuesOption) {
		return indexSelectContinuesOption == 2 ? true : false;
	}

	// Change state functions

	private boolean isJustForceChangeState() {
		return forceChangeState ? true : false;
	}

	public void changeToSelectProductState() {
		userState = UserState.SELECT_PRODUCT;
	}

	public void changeToAddingMoneyState() {
		userState = UserState.ADDING_MONEY;
	}

	public void changeStateImmediately() {
		forceChangeState = !forceChangeState;
	}

	/*
	 * This function will check the input option of user to detect the denomination,
	 * then forward it to addingMoneyByUser function
	 */

	public void checkOptionToAddMoney(byte inputOption) {
		switch (inputOption) {
		case 1:
			addingMoneyByUser(Currency.TEN.getDenomination());
			break;
		case 2:
			addingMoneyByUser(Currency.TWENTY.getDenomination());
			break;
		case 3:
			addingMoneyByUser(Currency.FIFTY.getDenomination());
			break;
		case 4:
			addingMoneyByUser(Currency.ONE_HUNDRED.getDenomination());
			break;
		case 5:
			addingMoneyByUser(Currency.TWO_HUNDRED.getDenomination());
			break;
		default:
			view.warningWrongDenomination();
			break;
		}
	}
}
