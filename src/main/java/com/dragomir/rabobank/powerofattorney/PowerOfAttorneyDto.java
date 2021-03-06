package com.dragomir.rabobank.powerofattorney;

import java.util.List;

import io.swagger.client.model.Direction;
import lombok.Data;

@Data
public class PowerOfAttorneyDto {
	 
	  private String id;

	  private String grantor;

	  private String grantee = null;

	  private String account = null;

	  private Direction direction = null;

	  private List<AuthorizationDtoType> authorizations = null;

	  private List<CardReferenceDto> cards = null;

}
