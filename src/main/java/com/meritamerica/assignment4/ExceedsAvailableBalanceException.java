package com.meritamerica.assignment4;

public class ExceedsAvailableBalanceException extends Exception{

	private static final long serialVersionUID = 1L;

	ExceedsAvailableBalanceException(String errorMessage){
		super(errorMessage);
	}
}
