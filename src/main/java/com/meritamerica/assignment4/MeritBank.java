package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*This is the MeritBank part of the assignment.
 * This interacts with CDOffering.java and AccountHolder.java
 * Created by Robert Johns
 */
public class MeritBank {

	// All private variables needed in the program
	private static int numOfAccountHolder = 0;
	private static long nextAccountNumber = 1;
	private static AccountHolder[] accountHolderArray = new AccountHolder[0];
	private static CDOffering[] cdOffering = new CDOffering[0];
	private static double totalValue = 0;
	static long accountNumber;
	static FraudQueue fraudQueue = new FraudQueue();

	// This adds another AccountHolder to the array
	static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] tempAccHolder = new AccountHolder[accountHolderArray.length + 1];
		for (int i = 0; i < accountHolderArray.length; i++) {
			tempAccHolder[i] = accountHolderArray[i];
		}
		tempAccHolder[numOfAccountHolder] = accountHolder;
		accountHolderArray = tempAccHolder;
		numOfAccountHolder++;
	}
	
	

	// getter for the AccountHolder array
	static AccountHolder[] getAccountHolders() {
		return accountHolderArray;
	}

	// getter for the CDOffering array
	static CDOffering[] getCDOfferings() {
		return cdOffering;
	}

	// compares the best value for CDAccount by its offerings and outputs that
	// offering
	static CDOffering getBestCDOffering(double depositAmount) {
		CDOffering temp = cdOffering[0];
		for (int x = 1; x < cdOffering.length; x++) {
			if (futureValue(depositAmount, cdOffering[x - 1].getInterestRate(),
					cdOffering[x - 1].getTerm()) < futureValue(depositAmount, cdOffering[x].getInterestRate(),
							cdOffering[x].getTerm())) {
				temp = cdOffering[x];
			}
		}
		return temp;
	}

	// compares the second best value for CDAccount by its offerings and outputs
	// that offering
	static CDOffering getSecondBestCDOffering(double depositAmount) {
		CDOffering temp = cdOffering[0];
		CDOffering temp2 = cdOffering[0];
		for (int x = 1; x < cdOffering.length; x++) {
			if (futureValue(depositAmount, cdOffering[x - 1].getInterestRate(),
					cdOffering[x - 1].getTerm()) < futureValue(depositAmount, cdOffering[x].getInterestRate(),
							cdOffering[x].getTerm())) {
				temp2 = temp;
				temp = cdOffering[x];
			}
		}
		return temp2;
	}

	// clears the offering array
	static void clearCDOfferings() {
		cdOffering = null;
	}

	// setter for the offering array
	static void setCDOfferings(CDOffering[] offerings) {
		cdOffering = offerings;
	}

	// getter for a new Account Number
	static long getNextAccountNumber() {
		return nextAccountNumber++;
	}

	// returns the total balance of the whole account holder array
	static double totalBalances() {
		for (AccountHolder x : accountHolderArray) {
			totalValue += x.getCombinedBalance();
		}
		return totalValue;
	}

	// returns the future value of what the user wants based off of parameters
	static double futureValue(double presentValue, double interestRate, int term) {
		return recursiveFutureValue(presentValue,term,interestRate);
	}
	
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		if(years > 0) {
			double newAmount = amount + (amount * interestRate);
			return recursiveFutureValue(newAmount, years - 1, interestRate);
		}
		return amount;
	}
// reads all the information from the txt file then sends the information off to where 
	// it needs to go weather that be checking savings cd or account holder classes
	public static boolean readFromFile(String fileName) {
		CDOffering[] CDOfferings = new CDOffering[0];
		AccountHolder[] accountHolders = new AccountHolder[0];
		setNextAccountNumber((long) 0);
		FraudQueue fraudQueue = new FraudQueue();
		Set<String> transactions = new HashSet<String>();
		try(BufferedReader nextLine = new BufferedReader(new FileReader(fileName))) {
			
			setNextAccountNumber(Long.valueOf(nextLine.readLine()));
			int numberOfCDOfferings = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i < numberOfCDOfferings; i++) {
				CDOfferings = Arrays.copyOf(CDOfferings, CDOfferings.length + 1);
				CDOfferings[CDOfferings.length - 1] = CDOffering.readFromString(nextLine.readLine());
				cdOffering = CDOfferings;
			}

			int numberOfAccountHolders = Integer.valueOf(nextLine.readLine());
			
			for(int i = 0; i < numberOfAccountHolders; i++) {
				AccountHolder nextAccountHolder = AccountHolder.readFromString(nextLine.readLine());
				accountHolders = Arrays.copyOf(accountHolders, accountHolders.length + 1);
				accountHolders[accountHolders.length - 1] = nextAccountHolder;
				accountHolderArray=accountHolders;	
				int numberOfCheckingAccounts = Integer.valueOf(nextLine.readLine());
				for(int c = 0; c < numberOfCheckingAccounts; c++) {
					nextAccountHolder.addCheckingAccount(CheckingAccount.readFromString(nextLine.readLine()));
					int numberOfCheckingTransactions = Integer.valueOf(nextLine.readLine());
					
					for(int ct = 0; ct < numberOfCheckingTransactions; ct++) {	
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfSavingsAccounts = Integer.valueOf(nextLine.readLine());
				
				for(int s = 0; s < numberOfSavingsAccounts; s++) {
					nextAccountHolder.addSavingsAccount(SavingsAccount.readFromString(nextLine.readLine()));
					int numberOfSavingsTransactions = Integer.valueOf(nextLine.readLine());
					for(int st = 0; st < numberOfSavingsTransactions; st++) {
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfCDAccounts = Integer.valueOf(nextLine.readLine());
				for(int cd = 0; cd < numberOfCDAccounts; cd++) {
					nextAccountHolder.addCDAccount(CDAccount.readFromString(nextLine.readLine()));
					int numberCDTransactions = Integer.valueOf(nextLine.readLine());
					for(int cdt = 0; cdt < numberCDTransactions; cdt++) {
						transactions.add(nextLine.readLine());
					}
				}

			}
			int numberOfFraudQueueTransactions = Integer.valueOf(nextLine.readLine());
			for(int fqt = 0; fqt < numberOfFraudQueueTransactions; fqt++) {
				fraudQueue.addTransaction(Transaction.readFromString(nextLine.readLine()));
			}
			System.out.println(transactions.size());
			for(String transaction : transactions) {
				Transaction newTran = Transaction.readFromString(transaction);
				if(newTran.getSourceAccount() == null) {
					newTran.getTargetAccount().addTransaction(newTran);
				}
				else {
					newTran.getTargetAccount().addTransaction(newTran);
					newTran.getSourceAccount().addTransaction(newTran);
				}
			}
			return true;
		}catch(Exception exception) {
			System.out.println(exception);
			return false;
		}
	}
// sets the account number
	private static void setNextAccountNumber(Long long1) {
		nextAccountNumber = long1;
	}
// sorts all the account holders in the account holders array
	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(accountHolderArray);
		for (AccountHolder a : accountHolderArray) {
			System.out.println(a.getCDBalance());
		}
		return accountHolderArray;
	}
// gets the information from all the other classes and writes that into a new txt file 
	static boolean writeToFile(String fileName) {
		try (BufferedWriter nextLine = new BufferedWriter(new FileWriter(fileName))){
        	nextLine.write(String.valueOf(nextAccountNumber));
        	nextLine.newLine();
        	nextLine.write(String.valueOf(cdOffering.length));
        	nextLine.newLine();
        	for(int cdo = 0; cdo < cdOffering.length; cdo++) {
        		nextLine.write(cdOffering[cdo].writeToString());
            	nextLine.newLine();
        	}
        	nextLine.write(String.valueOf(accountHolderArray.length));
        	nextLine.newLine();
        	for(int a = 0; a < accountHolderArray.length; a++) {
        		nextLine.write(accountHolderArray[a].writeToString());
        		nextLine.newLine();
        		nextLine.write(String.valueOf(accountHolderArray[a].getCheckingAccounts().length));
        		nextLine.newLine();
        		for(int c = 0; c<accountHolderArray[a].getCheckingAccounts().length; c++ ) {
        			nextLine.write(accountHolderArray[a].getCheckingAccounts()[c].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(accountHolderArray[a].getCheckingAccounts()[c].getTransactions().size()));
        			nextLine.newLine();
        			int ctl = accountHolderArray[a].getCheckingAccounts()[c].getTransactions().size();
        			for(int ct = 0; ct < ctl; ct++) {
        				nextLine.write(accountHolderArray[a].getCheckingAccounts()[c].getTransactions().get(ct).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(accountHolderArray[a].getSavingsAccounts().length));
        		nextLine.newLine();
        		for(int s = 0; s<accountHolderArray[a].getSavingsAccounts().length; s++ ) {
        			nextLine.write(accountHolderArray[a].getSavingsAccounts()[s].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(accountHolderArray[a].getSavingsAccounts()[s].getTransactions().size()));
        			nextLine.newLine();
        			int stl = accountHolderArray[a].getSavingsAccounts()[s].getTransactions().size();
        			for(int st = 0; st < stl; st++) {
        				nextLine.write(accountHolderArray[a].getSavingsAccounts()[s].getTransactions().get(st).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(accountHolderArray[a].getCDAccounts().length));
        		nextLine.newLine();
        		for(int cd = 0; cd<accountHolderArray[a].getCDAccounts().length; cd++ ) {
        			nextLine.write(accountHolderArray[a].getCDAccounts()[cd].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(accountHolderArray[a].getCDAccounts()[cd].getTransactions().size()));
        			nextLine.newLine();
        			int cdtl = accountHolderArray[a].getCDAccounts()[cd].getTransactions().size();
        			for(int cdt = 0; cdt < cdtl; cdt++) {
        				nextLine.write(accountHolderArray[a].getCDAccounts()[cd].getTransactions().get(cdt).writeToString());
        				nextLine.newLine();
        			}
        		}
        	}
        	nextLine.write(String.valueOf(fraudQueue.getTransaction().size()));
        	nextLine.newLine();
        	for(int fq = 0; fq < fraudQueue.getTransaction().size(); fq++) {
        		nextLine.write(fraudQueue.getTransaction().get(fq).writeToString());
        		nextLine.newLine();
        	}
    		nextLine.close();
        	return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}



	}
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException{
		
			BankAccount source = transaction.getSourceAccount();
			BankAccount target = transaction.getTargetAccount();
			
			if(source == null) {
				if(transaction instanceof WithdrawTransaction) {
					if(transaction.getAmount() < 0) {
						throw new NegativeAmountException("Can not withdraw a negative amount");
					}
					if(transaction.getAmount() + target.getBalance() < 0) {
						throw new ExceedsAvailableBalanceException("Insufficient Balance");
					}
					if(transaction.getAmount() < -1000) {
						fraudQueue.addTransaction(transaction);
						throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
					}

					return true;
				}
				if(transaction.getAmount() < 0) {
					throw new NegativeAmountException("Can not deposit a negative amount");
				}
				if(transaction.getAmount() > 1000) {
					fraudQueue.addTransaction(transaction);
					throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
				}
				return true;
			}
			if(source.getBalance() < transaction.getAmount()) {
				throw new ExceedsAvailableBalanceException("Insufficient Balance");
			}
			if(transaction.getAmount() < 0) {
				throw new NegativeAmountException("Can not transfer a negative amount");
			}
			if(transaction.getAmount() > 1000) {
				fraudQueue.addTransaction(transaction);
				throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
			}
			else{
				source.withdraw(transaction.amount);
				target.deposit(transaction.amount);

			}
			return true;
	}
	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	public static BankAccount getBankAccount(long accountId) {
//		if(accountId == 4) {
//			accountId = 12;
//		}
//		else if(accountId ==1) {
//			accountId = 11;
//		}
//		else if(accountId == 5) {
//			accountId = 13;
//		}
		for(AccountHolder account : accountHolderArray) {
 			for(int c = 0; c < account.getCheckingAccounts().length; c++) {
				if(accountId == account.getCheckingAccounts()[c].getAccountNumber()) {
					return account.getCheckingAccounts()[c];
				}
			}
			for(int s = 0; s < account.getSavingsAccounts().length; s++) {
				if(accountId == account.getSavingsAccounts()[s].getAccountNumber()) {
					return account.getSavingsAccounts()[s];
				}
			}
			for(int cda = 0; cda < account.getCDAccounts().length; cda++) {
				if(accountId == account.getCDAccounts()[cda].getAccountNumber()) {
					return account.getCDAccounts()[cda];
				}
			}
		
		}
		return null;
	}


}