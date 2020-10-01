package com.dragomir.rabobank.debitcard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.client.api.DebitCardApi;
import io.swagger.client.model.DebitCard;
import reactor.core.publisher.Mono;

@Service
public class DebitCardProxy {

	@Autowired
	private DebitCardApi debitCardApi;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Mono<DebitCardDto> getDebitCardDetail(String id) {
		return Mono.just(modelMapper.map(debitCardApi.getDebitCardDetail(id), DebitCardDto.class));
		
	}
}
