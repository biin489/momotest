package com.biin.momotest.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import com.biin.momotest.controller.SodaMachineController;
import com.biin.momotest.model.Currency;

public class View {

	SodaMachineController sodaMachineController;
	Scanner inputContent = new Scanner(System.in);

	public void setController(SodaMachineController sodaMachineController) {
		this.sodaMachineController = sodaMachineController;
	}

	// Print the header: Welcome notice, Balance, List product
	public void showSodaMachineScreen() {
		System.out.println("\n\n\n******************WELCOME TO THE SODA MACHINE******************");
		System.out.println();
		System.out.println("Your balance: " + sodaMachineController.getBalance());
		System.out.println();
		showListProduct();
		System.out.println("\n");
	}

	// Ask user to adding money by choosing option
	public void askUserForAddingMoney() {
		System.out.println("Please adding money to continues");
		System.out.println("Kindly choose a denomination:");
		printDenomination();
		System.out.println();
		System.out.println("To cancel or end your request, please press 0");
		byte indexSelectMoneyOption = 0;
		try {
			indexSelectMoneyOption = (byte) Integer.parseInt(inputContent.nextLine());
		} catch (Exception e) {
			warningWrongDenomination();
			sodaMachineController.changeStateImmediately();
			return;
		}
		if (sodaMachineController.isLegalDenominationSelected(indexSelectMoneyOption)) {
			sodaMachineController.checkOptionToAddMoney(indexSelectMoneyOption);
		} else {
			if (sodaMachineController.isCancelEndRequest(indexSelectMoneyOption)) {
				sodaMachineController.processCancelEndRequest();
			} else {
				sodaMachineController.changeStateImmediately();
				warningWrongDenomination();
			}
		}
	}

	// Ask use continues adding money or go to next step
	public void askAddingMoneyOrContinues() {
		System.out.println("Do you want to select product or continues adding money?");
		System.out.println("0. Cancel and get money back\t1. Continues adding money\t2. Select product");
		byte indexSelectContinuesOption = 0;
		try {
			indexSelectContinuesOption = (byte) Integer.parseInt(inputContent.nextLine());
		} catch (Exception e) {
			warningWrongSelected();
			return;
		}
		if (sodaMachineController.isLegalContinuesSelected(indexSelectContinuesOption)) {
			if (sodaMachineController.isGotoNextState(indexSelectContinuesOption)) {
				sodaMachineController.changeToSelectProductState();
			}
		} else {
			if (sodaMachineController.isCancelEndRequest(indexSelectContinuesOption)) {
				sodaMachineController.processCancelEndRequest();
			} else {
				warningWrongSelected();
			}
		}
	}

	// Ask if user want to continues select product or end request
	public void askSelectProductOrEnd() {
		System.out.println("Do you want to continues select product or end request?");
		System.out.println("0. End request\t1. Continues select product");
		byte indexSelectContinuesOption = 0;
		try {
			indexSelectContinuesOption = (byte) Integer.parseInt(inputContent.nextLine());
		} catch (Exception e) {
			warningWrongSelected();
			return;
		}
		if (sodaMachineController.isCancelEndRequest(indexSelectContinuesOption)) {
			sodaMachineController.processCancelEndRequest();
		} else {
			if (!sodaMachineController.isContinuesCurrentState(indexSelectContinuesOption)) {
				warningWrongSelected();
			}
		}
	}

	// Ask user to select product
	public void askUserForSelectingProduct() {
		System.out.println("Please select your drink that you wanted: ");
		System.out.println("(Input the number stand beside product name)");
		System.out.println("To cancel or end your request, please press 0");
		System.out.print("Your choose: ");
		byte indexSelectProductOption = 0;
		try {
			indexSelectProductOption = (byte) Integer.parseInt(inputContent.nextLine());
		} catch (Exception e) {
			warningWrongProduct();
			return;
		}
		if (sodaMachineController.isLegalProductSelected(indexSelectProductOption)) {
			sodaMachineController.selectProduct(sodaMachineController.getProduct(indexSelectProductOption));
		} else {
			if (sodaMachineController.isCancelEndRequest(indexSelectProductOption)) {
				sodaMachineController.processCancelEndRequest();
			} else {
				warningWrongProduct();
			}
		}
	}

	// Return money in balance and end process
	public void returnMoneyEndProcess(int returnMoney) {
		if (returnMoney > 0) {
			System.out.println("Please get your change back");
			System.out.println("Return money: " + returnMoney + "VND");
		}
		System.out.println();
		System.out.println("Thank for using Soda Machine. See you later!");
		System.out.println(
				"Tip: you can top up your Momo account, this e-wallet will make the payment more convenient and easier!");
	}

	// Below is warning notices
	public void warningWrongDenomination() {
		System.out.println("You entered the wrong denomination");
		System.out.println("Please try again!");
	}

	public void warningWrongProduct() {
		System.out.println("You entered the wrong product");
		System.out.println("Please try again!");
	}

	public void warningWrongSelected() {
		System.out.println("You entered the wrong selection");
		System.out.println("Please try again!");
	}

	public void warningNotEnoughMoney() {
		System.out.println("Your balance is not enough to purchase this product");
		System.out.println("Please insert money and try again!");
	}

	/*
	 * This function will print all currency denomination. But due to on Currency
	 * class has EMPTY_BALANCE(0) to define balance empty, so we have to ignore it
	 * by isIgnoreZeroOption.
	 * 
	 * When initialization function we set isIgnoreZeroOption to false (not ignore
	 * EMPTY_BALANCE yet), so when loop go to first element, we set it to true
	 * (ignore complete)
	 */

	private void printDenomination() {
		byte indexOption = 0;
		boolean isIgnoreZeroOption = false;
		for (Currency currencyDenomination : Currency.values()) {
			if (isIgnoreZeroOption) {
				System.out.print(indexOption + ". " + currencyDenomination.getDenomination() + " VND\t");
			} else {
				isIgnoreZeroOption = !isIgnoreZeroOption;
			}
			indexOption++;
		}
	}

	// Below are showing contents
	public void showListProduct() {
		sodaMachineController.showListProduct();
	}

	public void showAProduct(int productNo, String productName, int productQuantity, int productPrice) {
		System.out.print("\t" + productNo + ". " + productName + "(Quantity:" + productQuantity + ")\t" + productPrice
				+ " VND\t|");
	}

	public void showReturnedProduct(String releaseProductShowOut) {
		System.out.println("Please take your order product:");
		System.out.println(releaseProductShowOut);
	}

	public void productIsOutOfStock() {
		System.out.println("Your product that you're selected is out of stock");
		System.out.println("Please choose another product or cancel your request");
	}

	// Clear screen or cmd
	public static void clearSodaMachineScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033\143");
			}
		} catch (IOException | InterruptedException ex) {
		}
	}
}
