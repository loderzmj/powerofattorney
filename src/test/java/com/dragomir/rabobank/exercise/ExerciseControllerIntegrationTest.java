package com.dragomir.rabobank.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

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
		assertExpectedResponseForCard("0003", "[]");
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
