package com.dragomir.rabobank.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.dragomir.rabobank.creditcard.CreditCardProxy;
import com.dragomir.rabobank.debitcard.DebitCardProxy;
import com.dragomir.rabobank.powerofattorney.AuthorizationDtoType;
import com.dragomir.rabobank.powerofattorney.CardReferenceDto;
import com.dragomir.rabobank.powerofattorney.PowerOfAttorneyDto;
import com.dragomir.rabobank.powerofattorney.PowerOfAttorneyProxy;
import com.dragomir.rabobank.powerofattorney.PowerOfAttorneyReferenceDto;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RequestMapping("/rabobank/api/exercise")
@RestController
@Validated
public class ExerciseController {

	@Autowired
	private CreditCardProxy creditCardProxy;

	@Autowired
	private DebitCardProxy debitCardProxy;

	@Autowired
	private PowerOfAttorneyProxy powerOfAttorneyProxy;

	@GetMapping("/power-of-attorney/{id}/cards")
	public Flux<CardInfo> getCardsForPowerOfAttorney(@PathVariable("id") @NotNull String id) {
		PowerOfAttorneyDto powerOfAttorney = powerOfAttorneyProxy.getPowerOfAttorneyDetail(id).block();
		if (powerOfAttorney.getCards() == null || powerOfAttorney.getCards().isEmpty()) {
			return Flux.empty();
		}
		return Flux.fromIterable(powerOfAttorney.getCards())//
				.parallel().runOn(Schedulers.elastic())//
				.flatMap(this::getCardInfo)//
				.ordered((u1, u2) -> u2.getId().compareTo(u1.getId()));

	}

	@GetMapping("/power-of-attorney/card/{id}")
	public Mono<CardInfo> getCardInfoForGrantee(@PathVariable("id") @NotNull String id,
			@NotNull @RequestParam String grantee) {

		try {
			List<PowerOfAttorneyReferenceDto> allPoweOfAttorneyDtoReferences = powerOfAttorneyProxy
					.getAllPowerOfAttorneys().get();

			List<CompletableFuture<PowerOfAttorneyDto>> poaFutures = new ArrayList<>(
					allPoweOfAttorneyDtoReferences.size());
			allPoweOfAttorneyDtoReferences.stream().forEach(powerOfAttorneyReference -> poaFutures
					.add(powerOfAttorneyProxy.getPowerOfAttorneyDetailCF(powerOfAttorneyReference.getId())));

			CompletableFuture.allOf(poaFutures.toArray(new CompletableFuture[poaFutures.size()]));

			for (CompletableFuture<PowerOfAttorneyDto> poaFuture : poaFutures) {

				PowerOfAttorneyDto powerOfAttorneyDto = poaFuture.get();
				if (Objects.equals(powerOfAttorneyDto.getGrantee(), grantee) && powerOfAttorneyDto.getCards().stream()
						.anyMatch(cardReference -> Objects.equals(cardReference.getId(), id))) {
					Optional<CardReferenceDto> cardReferenceOpt = powerOfAttorneyDto.getCards().stream()
							.filter(cardReference -> Objects.equals(cardReference.getId(), id)).findFirst();
					if (isGranteeAuthorizedForCard(powerOfAttorneyDto, cardReferenceOpt.get())) {
						return getCardInfo(cardReferenceOpt.get());
					} else {
						throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
					}
				}

			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler(Exception ex) {
		HttpClientErrorException exception = (HttpClientErrorException) ex;
		return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), exception.getStatusCode());
	}

	private boolean isGranteeAuthorizedForCard(PowerOfAttorneyDto powerOfAttorneyDto,
			CardReferenceDto cardReferenceDto) {
		switch (cardReferenceDto.getType()) {
		case CREDIT_CARD:
			return powerOfAttorneyDto.getAuthorizations().contains(AuthorizationDtoType.CREDIT_CARD);

		case DEBIT_CARD:
			return powerOfAttorneyDto.getAuthorizations().contains(AuthorizationDtoType.DEBIT_CARD);

		default:
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	private Mono<CardInfo> getCardInfo(CardReferenceDto cardReference) {
		Mono<CardInfo> result;
		switch (cardReference.getType()) {
		case CREDIT_CARD:
			return creditCardProxy.getCreditCardDetail(cardReference.getId())//
					.map(creditCard -> ImmutableCardInfo.builder()//
							.id(creditCard.getId())//
							.cardNumber(creditCard.getCardNumber())//
							.holder(creditCard.getCardHolder())//
							.build());

		case DEBIT_CARD:
			return debitCardProxy.getDebitCardDetail(cardReference.getId())//
					.map(debitCard -> ImmutableCardInfo.builder()//
							.id(debitCard.getId())//
							.cardNumber(debitCard.getCardNumber())//
							.holder(debitCard.getCardHolder())//
							.build());

		default:
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

			
		}

	}
	
	@Value
	private class ErrorMessage {
		private String message;
	}

}
