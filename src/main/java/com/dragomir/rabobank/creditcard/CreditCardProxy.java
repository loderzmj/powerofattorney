package com.dragomir.rabobank.creditcard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.dragomir.rabobank.powerofattorney.ApiServiceException;

import io.swagger.client.api.CreditCardApi;
import reactor.core.publisher.Mono;

@Service
public class CreditCardProxy {
	@Autowired
	private CreditCardApi creditCardApi;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	public Mono<CreditCardDto> getCreditCardDetail(String id) {
		try {
			// bug in apidef.yaml 
			// expecting getCreditCardDetail instead of getDebitCardDetail
			return Mono.just(modelMapper.map(creditCardApi.getDebitCardDetail(id), CreditCardDto.class));
		} catch (HttpStatusCodeException e) {
			throw new ApiServiceException(e.getStatusCode(), String.format("CreditCardDetail with id: %s", id));
		} catch (ResourceAccessException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "CreditCard API not assessible");
		} catch (RestClientException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
		}
	}

}
