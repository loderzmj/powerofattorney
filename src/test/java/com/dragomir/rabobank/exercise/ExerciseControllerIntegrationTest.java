package com.dragomir.rabobank.exercise;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application.yaml")
public class ExerciseControllerIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void givenNotExistingPowerOfAttorney_whenGetCardsForPowerOfAttorney_thenReturn404() throws Exception {
		webTestClient.get().uri("/rabobank/api/exercise/power-of-attorney/{id}/cards", "1").exchange()//
				.expectStatus().isNotFound();
	}

	@Test
	public void givenPowerOfAttorneyWithoutCards_whenGetCardsForPowerOfAttorney_thenReturnEmpty() throws Exception {
		String expected = "[]";
		assertExpectedResponseForCard("0001", "[]");
	}

	@Test
	public void givenPowerOfAttorneyWithoutCards_whenGetCardsForPowerOfAttorney_thenReturnCards() throws Exception {
		String expected = "[{\"id\":\"3333\",\"holder\":\"Boromir\",\"cardNumber\":5075},{\"id\":\"2222\",\"holder\":\"Aragorn\",\"cardNumber\":6527},{\"id\":\"1111\",\"holder\":\"Frodo Basggins\",\"cardNumber\":1234}]";
		assertExpectedResponseForCard("0001", expected);
	}

	private void assertExpectedResponseForCard(String cardId, String expected) {
		webTestClient.get().uri("/rabobank/api/exercise/power-of-attorney/{id}/cards", cardId)//
				.exchange()//
				.expectStatus().isOk().expectBody().json(expected);
	}

}
