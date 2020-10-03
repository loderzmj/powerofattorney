package com.dragomir.rabobank.powerofattorney;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import io.swagger.client.api.PowerOfAttorneyApi;
import io.swagger.client.model.PowerOfAttorneyReference;
import reactor.core.publisher.Mono;

@Service
public class PowerOfAttorneyProxy {
	private static final String SERVER_ERROR = "Server error";
	private static final String POWER_OF_ATTORNEY_API_NOT_ACCESSIBLE = "PowerOfAttorney API not assessible";
	

	@Autowired
	private PowerOfAttorneyApi powerOfAttorneyApi;

	@Autowired
	private ModelMapper modelMapper;

	public Mono<PowerOfAttorneyDto> getPowerOfAttorneyDetail(String id) {
		try {
			return Mono.just(modelMapper.map(powerOfAttorneyApi.getPowerOfAttorneyDetail(id), PowerOfAttorneyDto.class));
		} catch (HttpStatusCodeException e) {
			throw new ApiServiceException(e.getStatusCode(), String.format("PowerOfAttorney with id: %s", id));
		} catch (ResourceAccessException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, POWER_OF_ATTORNEY_API_NOT_ACCESSIBLE);
		} catch (RestClientException e) {
			throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR);
		}

	}

	public CompletableFuture<PowerOfAttorneyDto> getPowerOfAttorneyDetailCF(String id) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return modelMapper.map(powerOfAttorneyApi.getPowerOfAttorneyDetail(id), PowerOfAttorneyDto.class);
			} catch (HttpStatusCodeException e) {
				throw new ApiServiceException(e.getStatusCode(), String.format("PowerOfAttorney with id: %s", id));
			} catch (ResourceAccessException e) {
				throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, POWER_OF_ATTORNEY_API_NOT_ACCESSIBLE);
			} catch (RestClientException e) {
				throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR);
			}
		});
	}

	public CompletableFuture<List<PowerOfAttorneyReferenceDto>> getAllPowerOfAttorneys() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				List<PowerOfAttorneyReference> allPowerOfAttorneys = powerOfAttorneyApi.getAllPowerOfAttorneys();
				return allPowerOfAttorneys.stream()//
						.map(powerOfAttorney -> modelMapper.map(powerOfAttorney, PowerOfAttorneyReferenceDto.class))//
						.collect(Collectors.toList());
			} catch (HttpStatusCodeException e) {
				throw new ApiServiceException(e.getStatusCode(), "Get all PowerOfAttorney");
			} catch (ResourceAccessException e) {
				throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, POWER_OF_ATTORNEY_API_NOT_ACCESSIBLE);
			}
			catch (RestClientException e) {
				throw new ApiServiceException(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR);
			}

		});
	}

}
