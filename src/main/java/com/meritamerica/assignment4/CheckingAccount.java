package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Created by Behulum W
public class CheckingAccount extends BankAccount {

	private static final double INTEREST_RATE = .0001;

	public CheckingAccount(double openingBalance) {

		super(openingBalance, INTEREST_RATE);

	}
	public CheckingAccount (long accountNumber, double openBalance, double interestRate, Date accountOpenedOn) {
		super(accountNumber, openBalance, interestRate, accountOpenedOn);
	}
	//storing the data we got from reading the file into the checking account 
	public static CheckingAccount readFromString(String accountData)throws java.lang.NumberFormatException, ParseException {
    	
		String [] holding = accountData.split(",");
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		//[0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which is SimpleDate
		long accountNumber = Long.parseLong(holding[0]);
		double balance = Double.parseDouble(holding[1]);
		double interestRate = Double.parseDouble(holding[2]);
		Date accountOpenedOn = date.parse(holding[3]);
		CheckingAccount newCheckingAccount = new CheckingAccount(accountNumber, balance, interestRate, accountOpenedOn);
		return newCheckingAccount;
	}


}