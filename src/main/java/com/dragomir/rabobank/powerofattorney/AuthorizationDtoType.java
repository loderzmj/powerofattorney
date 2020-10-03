package com.dragomir.rabobank.powerofattorney;

public enum AuthorizationDtoType {

	  DEBIT_CARD("DEBIT_CARD"),
	  
	  CREDIT_CARD("CREDIT_CARD"),
	  
	  VIEW("VIEW"),
	  
	  PAYMENT("PAYMENT");

	  private String value;

	  AuthorizationDtoType(String value) {
	    this.value = value;
	  }

	  @Override
	  public String toString() {
	    return String.valueOf(value);
	  }

}
