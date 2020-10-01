package com.dragomir.rabobank.powerofattorney;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.client.api.PowerOfAttorneyApi;
import io.swagger.client.model.PowerOfAttorneyReference;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PowerOfAttorneyProxy {

	@Autowired
	private PowerOfAttorneyApi powerOfAttorneyApi;

	@Autowired
	private ModelMapper modelMapper;

	public Mono<PowerOfAttorneyDto> getPowerOfAttorneyDetail(String id) {
		return Mono.just(modelMapper.map(powerOfAttorneyApi.getPowerOfAttorneyDetail(id), PowerOfAttorneyDto.class));
	}

	public CompletableFuture<PowerOfAttorneyDto> getPowerOfAttorneyDetailCF(String id) {
		return CompletableFuture.supplyAsync(() -> {
			return modelMapper.map(powerOfAttorneyApi.getPowerOfAttorneyDetail(id), PowerOfAttorneyDto.class);
		});
	}

	public CompletableFuture<List<PowerOfAttorneyReferenceDto>> getAllPowerOfAttorneys() {
		return CompletableFuture.supplyAsync(() -> {
			List<PowerOfAttorneyReference> allPowerOfAttorneys = powerOfAttorneyApi.getAllPowerOfAttorneys();
			return allPowerOfAttorneys.stream()//
					.map(powerOfAttorney -> modelMapper.map(powerOfAttorney, PowerOfAttorneyReferenceDto.class))//
					.collect(Collectors.toList());
		});
	}

}
