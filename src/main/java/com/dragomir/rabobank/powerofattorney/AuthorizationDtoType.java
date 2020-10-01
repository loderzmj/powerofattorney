package com.dragomir.rabobank.powerofattorney;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.client.model.Authorization;

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
