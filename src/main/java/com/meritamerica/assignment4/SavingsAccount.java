package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*Savings Account class for user
 * Created by Robert J
 */
public class SavingsAccount extends BankAccount{

	// All constants needed in class
	private static final double INTEREST_RATE = .01;

	// Constructor that adds balance and interest to the account 
	public SavingsAccount (double openingBalance) {
		
		super(openingBalance, INTEREST_RATE);
		
	}
	
	public SavingsAccount (long accountNumber, double openBalance, double interestRate, Date accountOpenedOn) {
		super(accountNumber, openBalance, interestRate, accountOpenedOn);
	}
	//storing the data we got from reading the file into the savings account 
	public static SavingsAccount readFromString(String accountData)throws ParseException, NumberFormatException {
    	String [] holding = accountData.split(",");
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    	//[0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which is SimpleDate
    	long accountNumber = Long.parseLong(holding[0]);
    	double balance = Double.parseDouble(holding[1]);
    	double interestRate = Double.parseDouble(holding[2]);
    	Date accountOpenedOn = date.parse(holding[3]);

    	return new SavingsAccount(accountNumber, balance, interestRate, accountOpenedOn);
	}

	

}