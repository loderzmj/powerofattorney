package com.dragomir.rabobank.exercise;

import org.immutables.value.Value;

@Value.Immutable
public interface CardInfo {
	String getId();

	String getHolder();

	int getCardNumber();
}
