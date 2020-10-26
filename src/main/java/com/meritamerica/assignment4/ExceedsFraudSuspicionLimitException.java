package com.meritamerica.assignment4;

public class ExceedsFraudSuspicionLimitException extends Exception{



	ExceedsFraudSuspicionLimitException(String errorMessage){
		super(errorMessage);
	}
}
