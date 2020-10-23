package com.meritamerica.assignment4;

import java.util.Arrays;

/*This program is the main account holder for the project.
 *This creates a checking and savings account for the user to withdraw and deposit money.
 *It calls the different to strings from each and outputs it all into one string.
 *It takes the full name, ssn, and beginning balance for checking
 *  and savings of the user as well for the unique bank account.
 * Created by Behulum W and Robert J
 */
public class AccountHolder implements Comparable<AccountHolder>  {
	

	// All private variables needed in the program
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	private double checkingAccountBalance;
	private double savingsAccountBalance;
	private double cdAccountBalance;
	private int numberOfCDAccount;
	private int numberOfSavingsAccount;
	private int numberOfCheckingAccount;
	private double totalBalance;
	private SavingsAccount[] savingsAccount = new SavingsAccount[0];
	private CheckingAccount[] checkingAccount = new CheckingAccount[0];
	private CDAccount[] cdAccount = new CDAccount[0];

	/*
	 * Default constructor Created by Behulum W
	 */
	public AccountHolder() {

	}

	/*
	 * Constructor with initial values Created by Robert J
	 */
	public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.numberOfSavingsAccount = 0;
		this.numberOfCheckingAccount = 0;
		this.numberOfCDAccount = 0;
		this.cdAccountBalance = 0;
		this.checkingAccountBalance = 0;
		this.savingsAccountBalance = 0;
		this.totalBalance = 0;
	}

	/*
	 * Setter for first name created by behulum w
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * getter for first name created by behulum w
	 */
	public String getFirstName() {
		return firstName;
	}

	/*
	 * Setter for middle name created by Robert J
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/*
	 * getter for middle name created by Robert J
	 */
	public String getMiddleName() {
		return middleName;
	}

	/*
	 * Setter for last name created by behulum w
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * getter for last name created by behulum w
	 */
	public String getLastName() {
		return lastName;
	}

	/*
	 * Setter for Social Security Number created by Robert J
	 */
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	/*
	 * getter for Social Security Number created by Robert J
	 */
	public String getSSN() {
		return ssn;
	}

	/*
	 * Adds new Checking Account if the total is below $250,000 before adding for
	 * checking and savings combined. created by Robert J
	 */
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsFraudSuspicionLimitException, NegativeAmountException, ExceedsCombinedBalanceLimitException {
		
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		CheckingAccount newAccount = new CheckingAccount(openingBalance);
		
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		/* A manual way to create the array.copy method.
		* Creating a temp array that will look identical to the old array with an additional null index at the end
		* then sets the newly created account to the null index/ last index.
		*/
		
		CheckingAccount[] holding = new CheckingAccount[this.checkingAccount.length + 1];
		for(int i = 0; i<this.checkingAccount.length; i++) {
			holding[i] = this.checkingAccount[i];
		}
		holding[holding.length - 1] = newAccount;
		this.checkingAccount = holding;
		return newAccount;
	}

	/*
	 * Adds new Checking Account if the total is below $250,000 before adding for
	 * checking and savings combined. created by Robert J
	 */
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsFraudSuspicionLimitException, ExceedsCombinedBalanceLimitException, NegativeAmountException {
		
		if(getCheckingBalance() + getSavingsBalance() + checkingAccount.getBalance() >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		CheckingAccount newAccount = new CheckingAccount(checkingAccount.getBalance());
		
		DepositTransaction transaction = new DepositTransaction(newAccount, checkingAccount.getBalance());
		
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		/* A manual way to create the array.copy method.
		* Creating a temp array that will look identical to the old array with an additional null index at the end
		* then sets the newly created account to the null index/ last index.
		*/
		
		CheckingAccount[] holding = new CheckingAccount[this.checkingAccount.length + 1];
		for(int i = 0; i<this.checkingAccount.length; i++) {
			holding[i] = this.checkingAccount[i];
		}
		holding[holding.length - 1] = newAccount;
		this.checkingAccount = holding;
		return newAccount;
	}

	/*
	 * getter for checking Account array created by Robert J
	 */
	public CheckingAccount[] getCheckingAccounts() {
		return checkingAccount;
	}

	/*
	 * getter for number of checking accounts created by Robert Johns
	 */
	public int getNumberOfCheckingAccounts() {
		return numberOfCheckingAccount;
	}

	/*
	 * getter for checking balance for all accounts together created by Robert J
	 */
	public double getCheckingBalance() {
		checkingAccountBalance = 0;
		for (CheckingAccount x : checkingAccount) {
			checkingAccountBalance += x.getBalance();
		}
		return checkingAccountBalance;
	}

	/*
	 * Adds new Saving Account if the total is below $250,000 before adding for
	 * checking and savings combined. created by Robert J
	 */
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsFraudSuspicionLimitException, NegativeAmountException, ExceedsCombinedBalanceLimitException {
		
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		SavingsAccount newAccount = new SavingsAccount(openingBalance);
		
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		/* A manual way to create the array.copy method.
		* Creating a temp array that will look identical to the old array with an additional null index at the end
		* then sets the newly created account to the null index/ last index.
		*/
		
		SavingsAccount[] holding = new SavingsAccount[savingsAccount.length + 1];
		for(int i = 0; i<savingsAccount.length; i++) {
			holding[i] = savingsAccount[i];
		}
		holding[holding.length - 1] = newAccount;
		savingsAccount = holding;
		return newAccount;
	}

	/*
	 * Adds new savings account in the array of savings account created by Robert
	 * Johns
	 */
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsFraudSuspicionLimitException, NegativeAmountException, ExceedsCombinedBalanceLimitException {
		if(getCheckingBalance() + getSavingsBalance() + savingsAccount.getBalance()>= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		DepositTransaction transaction = new DepositTransaction(savingsAccount, savingsAccount.getBalance());
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
		/* A manual way to create the array.copy method.
		* Creating a temp array that will look identical to the old array with an additional null index at the end
		* then sets the newly created account to the null index/ last index.
		*/
		
		SavingsAccount[] holding = new SavingsAccount[this.savingsAccount.length + 1];
		for(int i = 0; i<this.savingsAccount.length; i++) {
			holding[i] = this.savingsAccount[i];
		}
		holding[holding.length - 1] = savingsAccount;
		this.savingsAccount = holding;
		return savingsAccount;
	}

	/*
	 * getter for saving Account array created by Robert J
	 */
	public SavingsAccount[] getSavingsAccounts() {
		return savingsAccount;
	}

	/*
	 * getter for number of savings accounts created by Robert Johns
	 */
	public int getNumberOfSavingsAccounts() {
		return numberOfSavingsAccount;
	}

	/*
	 * getter for saving balance for all accounts together created by Robert J
	 */
	public double getSavingsBalance() {
		savingsAccountBalance = 0;
		for (SavingsAccount y : savingsAccount) {
			savingsAccountBalance += y.getBalance();
		}
		return savingsAccountBalance;
	}

	/*
	 * adds to cdAccount array if parameters of a new cdAccount are passed in
	 * created by Robert Johns
	 */
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws NegativeAmountException, ExceedsFraudSuspicionLimitException {
		
//		Should also add a deposit transaction with the opening balance
	
		if(offering == null) {
			return null;
		}
		CDAccount newAccount = new CDAccount(offering, openingBalance);
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
		// Similar to manual portion of creating an array 1 index larger.
		CDAccount[] holding = Arrays.copyOf(cdAccount, cdAccount.length+1);
		
		for(int i = 0; i<cdAccount.length; i++) {
			holding[i] = cdAccount[i];
		}
		holding[holding.length - 1] = newAccount;
		cdAccount = holding;
		return newAccount;
	}

	/*
	 * adds to cdAcount array if CDAccount class is the parameter created by Robert
	 * Johns
	 */
	public CDAccount addCDAccount(CDAccount cdAccount) throws ExceedsFraudSuspicionLimitException, NegativeAmountException {
		
		DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		CDAccount[] holding = Arrays.copyOf(this.cdAccount,this.cdAccount.length+1);
		for(int i = 0; i<this.cdAccount.length; i++) {
			holding[i] = this.cdAccount[i];
		}
		holding[holding.length - 1] = cdAccount;
		this.cdAccount = holding;
		return cdAccount;
	}

	/*
	 * getter for cdAccount array created by Robert Johns
	 */
	public CDAccount[] getCDAccounts() {
		return cdAccount;
	}

	/*
	 * getter for number of CDAccounds created by Robert Johns
	 */
	public int getNumberOfCDAccounts() {
		return numberOfCDAccount;
	}

	public double getCDBalance() {
		cdAccountBalance = 0;
		for (CDAccount z : cdAccount) {
			cdAccountBalance += z.getBalance();
		}
		return cdAccountBalance;
	}

	/*
	 * getter for total balance for all accounts together created by Robert J
	 */
	public double getCombinedBalance() {
		/*
		 * totalBalance = checkingAccountBalance + savingsAccountBalance +
		 * cdAccountBalance; return totalBalance;
		 */
		return (getCheckingBalance() + getSavingsBalance() + getCDBalance());
	}

// writing the data we have stored in account holder to a string and sending it to txt file
	public String writeToString() {
    	StringBuilder accountHolderData = new StringBuilder();
    	accountHolderData.append(firstName).append(",");
    	accountHolderData.append(middleName).append(",");
    	accountHolderData.append(lastName).append(",");
    	accountHolderData.append(ssn);
    	return accountHolderData.toString();
    }
	
	//storing the data we got from reading the file into the  account holder
	public static AccountHolder readFromString(String accountHolderData) {
	    String[] holding = accountHolderData.split(",");
	    String firstName = holding[0];
	    String middleName = holding[1];
	    String lastName = holding[2];
	    String ssn = holding[3];	
	    return new AccountHolder(firstName, middleName, lastName, ssn);
	}
	@Override
	public int compareTo(AccountHolder account) {
		if(this.getCombinedBalance() > account.getCombinedBalance()) {
			return 1;
		} else {
			return -1;
		}
	}


	
}