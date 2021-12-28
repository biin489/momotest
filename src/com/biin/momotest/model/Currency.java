package com.biin.momotest.model;

public enum Currency {
	EMPTY_BALANCE(0), TEN(10000), TWENTY(20000), FIFTY(50000), ONE_HUNDRED(100000), TWO_HUNDRED(200000);

	int denomination;

	private Currency(int inputDenomination) {
		denomination = inputDenomination;
	}

	public int getDenomination() {
		return denomination;
	}
}
