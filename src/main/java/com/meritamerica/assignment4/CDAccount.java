package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CDAccount extends BankAccount{
	private CDOffering offering = null;
	private Date startDate;
	private double balance;
	private int term;
	
	//*created by behulum w
	
	CDAccount(CDOffering offering, double balance){
		super(balance, offering.getInterestRate());
		this.offering = offering;
		this.startDate= new Date();
	}
	
	public CDAccount(long accountNumber, double openBalance, double interestRate, Date accountOpenedOn, int term) {
		super(accountNumber, openBalance, interestRate, accountOpenedOn);
		this.term = term;
	}

	public double getBalance() {
		return super.getBalance();
	}
	
	public double getInterestRate() {
		return super.interestRate;
	}
	
	public int getTerm() {
		return term;
	}
	
	public Date getStartDate(){
		return this.startDate;
	}

	// created by behulum w
	
	public long getAccountNumber() {
		return super.accountNumber;
	}
	// not able to withdraw money from CD Account till term is up
	public boolean withdraw(double amount) {
		Date date = new Date();  
		int years =   startDate.getYear() -date.getYear();
		if(years> term)
		{
	        return true;
		}
		
        return false;
    }
    // not able to deposit more money into CD Account till term is up
    public boolean deposit(double amount) {
		Date date = new Date();  
		int years =   startDate.getYear() -date.getYear();
		if(years> term)
		{
	        return true;
		}
		
        return false;
    }
	
	public double futureValue() {
		return (super.getBalance() * Math.pow(1.0 + offering.getInterestRate(), offering.getTerm()));
		
		
	}
	
	//storing the data we got from reading the file into the cd account 
	public static CDAccount readFromString(String accountData)throws ParseException, NumberFormatException {
    	String [] holding = accountData.split(",");
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    	long accountNumber = Long.parseLong(holding[0]);
    	double balance = Double.parseDouble(holding[1]);
    	double interestRate = Double.parseDouble(holding[2]);
    	Date accountOpenedOn = date.parse(holding[3]);
    	int term = Integer.parseInt(holding[4]);
    	CDAccount newCDAccount = new CDAccount(accountNumber,balance,interestRate,accountOpenedOn,term);
    	return newCDAccount;
    }
	
	// writing the data we have stored in cd account to a string and sending it to txt file   
    public String writeToString() {
    	StringBuilder override = new StringBuilder();
    	override.append(writeToString()).append(",");
    	override.append(getTerm());
    	return override.toString();
    }

}