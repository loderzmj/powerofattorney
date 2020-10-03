package com.dragomir.rabobank.debitcard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.dragomir.rabobank.powerofattorney.ApiServiceException;

import io.swagger.client.api.DebitCardApi;
import reactor.core.publisher.Mono;

@Service
public class DebitCardProxy {

	@Autowired
	private DebitCardApi debitCardApi;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Mono<DebitCardDto> getDebitCardDetail(String id) {
		try {
			return Mono.just(modelMapper.map(debitCardApi.getDebitCardDetail(id), DebitCardDto.class));
		} catch (HttpStatusCodeException e) {
			throw new ApiServiceException(e.getStatusCode(), String.format("DebitCardDetail with id: %s", id));
		} catch (ResourceAccessException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "DebitCard API not assessible");
		} catch (RestClientException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
		}
		
	}
}
