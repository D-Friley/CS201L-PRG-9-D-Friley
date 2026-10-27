public class Checking extends BankAccount {
   
    //overloaded constructor
    public Checking(int accountNumber, String accountType, String firstName, String lastName,
                       String transactionDate, double transactionAmount,
                       double accountBalance, double interestEarned){
                        super (accountNumber, accountType, firstName, lastName,
                               transactionDate, transactionAmount,
                               accountBalance, interestEarned);
                       }
    
     //Create Override Functions for processInterestEarned
    @Override
    public void processInterestEarned() {
        return; //Checking accounts do not earn interest 
     
}
    }
