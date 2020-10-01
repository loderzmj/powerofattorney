package com.dragomir.rabobank.creditcard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.client.api.CreditCardApi;
import reactor.core.publisher.Mono;

@Service
public class CreditCardProxy {
	@Autowired
	private CreditCardApi creditCardApi;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	public Mono<CreditCardDto> getCreditCardDetail(String id) {
		return Mono.just(modelMapper.map(creditCardApi.getDebitCardDetail(id), CreditCardDto.class));
	}

}
