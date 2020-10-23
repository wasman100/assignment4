package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.List;

public class FraudQueue {
	private List<Transaction> transactions = new ArrayList<Transaction>();

	FraudQueue() {

	}

	void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	List<Transaction> getTransaction() {
		return transactions;
	}

}
