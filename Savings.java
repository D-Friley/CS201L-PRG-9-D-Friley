public class Savings extends BankAccount {
        public Savings(int accountNumber, String accountType, String firstName, String lastName,
                       String transactionDate, double transactionAmount,
                       double accountBalance, double interestEarned){
                        super (accountNumber, accountType, firstName, lastName,
                               transactionDate, transactionAmount,
                               accountBalance, interestEarned);
                       }
    
          //Create Override Functions for processInterestEarned
        @Override
        public void processInterestEarned() {
                double annualRate;

                if (accountBalance >= 100.00 && accountBalance <= 999.99) {
                        annualRate = 0.05;  // 5% annual interest
                } else {
                        annualRate = 0.075; // 7.5% annual interest
    }

                double interest = accountBalance * annualRate * 5.0 / 365.0;

                interestEarned += interest;
                accountBalance += interest;
}
}
