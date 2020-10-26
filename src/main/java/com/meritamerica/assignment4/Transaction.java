package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {


	BankAccount account;
	BankAccount targetAccount;
	double amount;
	Date date;

	public BankAccount getSourceAccount() {
		return account;
	}
		
	public void setSourceAccount(BankAccount account) {
		this.account = account;
	}
	
	public BankAccount getTargetAccount() {
		return targetAccount;
	}
	
	public void setTargetAccount(BankAccount targetAccount) {
		this.targetAccount = targetAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Date getTransactionDate(){
		return date;
	}
	
	public void setTransactionDate(Date date) {
		this.date = date;
	}
	/**
	 * A method that writes information that writes the date passed through into a string.
	 * Reformatted date to be simpler. 
	 */
	public String writeToString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder toString = new StringBuilder();
		if(account == null) {
			toString.append(-1);
		}
		else {
			toString.append(account.getAccountNumber());
		}
		toString.append(",");
		toString.append(targetAccount.getAccountNumber());
		toString.append(",");
		toString.append(amount);
		toString.append(",");
		toString.append(dateFormat.format(date));
		return toString.toString();
	}
	/**
	 *Read from String , gets data thats being passed through, and returns Transaction with the information 
	 */
	public static Transaction readFromString(String transactionDataString) throws ParseException {
		String[] temp = transactionDataString.split(",");
		
		BankAccount source;
		if(temp[0].equals("-1")) {
			source = null;
		}
		else {
			source = MeritBank.getBankAccount(Long.valueOf(temp[0]));
		}
		
		BankAccount target = MeritBank.getBankAccount(Long.valueOf(temp[1]));
		
		double amount = Double.valueOf(temp[2]);
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(temp[3]);

		if(Integer.valueOf(temp[0]) == -1) {
			if(Double.valueOf(temp[2]) < 0) {
				WithdrawTransaction transaction = new WithdrawTransaction(target, amount);
				transaction.setTransactionDate(date);
				System.out.println(transaction.writeToString());
				return transaction;
			}
			else {
				DepositTransaction transaction = new DepositTransaction(target, amount);
				transaction.setTransactionDate(date);
				System.out.println(transaction.writeToString());
				return transaction;
			}
		}
		else {
			TransferTransaction transaction = new TransferTransaction(source, target, amount);
			System.out.println(transaction.writeToString());
			return transaction;
		}
	}	
}
