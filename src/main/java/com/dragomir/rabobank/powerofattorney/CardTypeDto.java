package com.dragomir.rabobank.powerofattorney;

public enum CardTypeDto {
	DEBIT_CARD("DEBIT_CARD"),

	CREDIT_CARD("CREDIT_CARD");

	private String value;

	CardTypeDto(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
