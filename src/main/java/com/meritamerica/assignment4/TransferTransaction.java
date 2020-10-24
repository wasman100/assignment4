package com.meritamerica.assignment4;
import java.util.Date;
public class TransferTransaction extends Transaction{

	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount){

		
			this.account = sourceAccount;
			this.targetAccount = targetAccount;
			this.amount = amount;
			this.date = new Date();
		
	}

}
