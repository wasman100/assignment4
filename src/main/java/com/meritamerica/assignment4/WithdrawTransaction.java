package com.meritamerica.assignment4;

import java.util.Date;

public class WithdrawTransaction extends Transaction {

	WithdrawTransaction(BankAccount targetAccount, double amount) {

		account = targetAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.date = new Date();

	}

}
