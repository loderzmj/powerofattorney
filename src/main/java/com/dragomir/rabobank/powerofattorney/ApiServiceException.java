package com.dragomir.rabobank.powerofattorney;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@EqualsAndHashCode(callSuper=false)
public class ApiServiceException extends RuntimeException {
	private HttpStatus statusCode;
	private String message;
}
