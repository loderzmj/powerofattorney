package com.dragomir.rabobank.debitcard;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.client.model.Limit;
import lombok.Data;

@Data
public class DebitCardDto {
	
	  private String id;
	  
	  private Integer cardNumber;

	  private Integer sequenceNumber;

	  private String cardHolder;

	  private Limit atmLimit;

	  private Limit posLimit;

	  private Boolean contactless;
}
