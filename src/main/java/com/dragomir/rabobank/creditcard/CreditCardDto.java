package com.dragomir.rabobank.creditcard;

import java.util.List;

import com.dragomir.rabobank.powerofattorney.AuthorizationDtoType;
import com.dragomir.rabobank.powerofattorney.CardReferenceDto;
import com.dragomir.rabobank.powerofattorney.PowerOfAttorneyDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.client.model.Direction;
import lombok.Data;

@Data
public class CreditCardDto {
	
	  private String id;
  
	  private Integer cardNumber;

	  private Integer sequenceNumber;

	  private String cardHolder;

	  private Integer monhtlyLimit;
}
