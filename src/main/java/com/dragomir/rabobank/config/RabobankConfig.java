package com.dragomir.rabobank.config;

import io.swagger.client.ApiClient;
import io.swagger.client.api.CreditCardApi;
import io.swagger.client.api.DebitCardApi;
import io.swagger.client.api.PowerOfAttorneyApi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabobankConfig {

	@Value("${rabobank.json-stub.url}")
	private String hostJsonStub;

	@Bean
	public ApiClient apiClient() {
		return new ApiClient().setBasePath(hostJsonStub);
	}

	@Bean
	public CreditCardApi creditCardApi() {
		return new CreditCardApi(apiClient());
	}

	@Bean
	public DebitCardApi debitCardApi() {
		return new DebitCardApi(apiClient());
	}

	@Bean
	public PowerOfAttorneyApi powerOfAttorneyApi() {
		return new PowerOfAttorneyApi(apiClient());
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
