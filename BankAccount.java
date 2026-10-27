
import java.io.PrintWriter;

//Create the Abstract Class
public abstract class BankAccount {
    protected int accountNumber;
    protected String accountType; // C, S, CD
    protected String firstName;
    protected String lastName;
    protected String transactionDate;
    protected double transactionAmount;
    protected double accountBalance;
    protected double interestEarned;

    //Default Constructor
    public BankAccount(){
        this.accountNumber = 0;
        this.accountType = "";
        this.firstName = "";
        this.lastName = "";
        this.transactionDate = "";
        this.transactionAmount = 0;
        this.accountBalance = 0;
        this.interestEarned = 0;
    }

    //Overloaded constructor 
    public BankAccount(int accountNumber, String accountType, String firstName, String lastName,
                       String transactionDate, double transactionAmount,
                       double accountBalance, double interestEarned) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.transactionDate = transactionDate; 
        this.transactionAmount = transactionAmount;
        this.accountBalance = accountBalance;
        this.interestEarned = interestEarned;
    }

    // Getters
    // Complete as needed
    public int getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getAccountBalance() { return accountBalance; }
    public double getInterestEarned() { return interestEarned; }



    // Setters
    public void setAccountBalance(double balance) { this.accountBalance = balance; }
    public void setInterestEarned(double interest) { this.interestEarned = interest; }

    // Print formatted account details
    public void printDetails(PrintWriter out) {
        out.printf("%-20d%-20s%-20s%-15s%10.2f%15.2f%n",
                   accountNumber, accountType, firstName, lastName,
                   accountBalance, interestEarned);
    }

    public void processDeposit(double amt){
        //add logic for this function)) {
            this.accountBalance += amt;
        }

    public boolean processWithdrawal(double amt) {
        if (amt <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
    }

        if (amt > accountBalance) {
            System.out.println("Insufficient funds.");
            return false;
    }

        accountBalance -= amt;
        return true;
}
       
    

    public void processInterestEarned(){ 
        this.interestEarned += 0;
        this.accountBalance += 0;
    }
}
