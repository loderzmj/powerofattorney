package com.dragomir.rabobank.powerofattorney;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.client.model.CardType;
import lombok.Data;

@Data
public class CardReferenceDto {
	private String id;

	private CardTypeDto type;

}
