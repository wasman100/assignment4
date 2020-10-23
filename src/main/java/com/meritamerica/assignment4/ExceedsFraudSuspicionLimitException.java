package com.meritamerica.assignment4;

public class ExceedsFraudSuspicionLimitException extends Exception{

	private static final long serialVersionUID = 1L;

	ExceedsFraudSuspicionLimitException(String errorMessage){
		super(errorMessage);
	}
}
