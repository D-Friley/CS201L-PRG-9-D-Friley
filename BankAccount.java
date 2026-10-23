
import java.io.PrintWriter;

//Create the Abstract Class
public abstract class BankAccount {
    protected int accountNumber;
    protected String accountType; // C, S, CD
    protected String firstName;
    protected String lastName;
    protected String transactionDate;
    protected int transactionAmount;
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
                       String transactionDate, int transactionAmount,
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


    // Setters
    public void setAccountBalance(double balance) { this.accountBalance = balance; }
    public void setInterestEarned(double interest) { this.interestEarned = interest; }

    // Print formatted account details
    public void printDetails(PrintWriter out) {
        out.printf("%-20d%-20s%-20s%-15s%10.2f%15.2f%n",
                   accountNumber, accountType, firstName, lastName,
                   accountBalance, interestEarned);
    }

    public void processDeposit(int amt){
        //add logic for this function
        
    }

    public void processWithdrawal(int amt){
        //add logic for this function
       
    }

    public void processInterestEarned(){ 
        this.interestEarned += 0;
        this.accountBalance += 0;
    }
}
