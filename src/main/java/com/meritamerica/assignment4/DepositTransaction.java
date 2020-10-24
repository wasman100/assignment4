package com.meritamerica.assignment4;
import java.util.Date;
public class DepositTransaction extends Transaction{
	
	DepositTransaction(BankAccount targetAccount, double amount){
		account = targetAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.date = new Date();
	}

}
