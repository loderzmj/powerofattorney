package com.dragomir.rabobank.creditcard;

import lombok.Data;

@Data
public class CreditCardDto {
	
	  private String id;
  
	  private Integer cardNumber;

	  private Integer sequenceNumber;

	  private String cardHolder;

	  private Integer monhtlyLimit;
}
