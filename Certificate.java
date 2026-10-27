public class Certificate extends BankAccount{
    private boolean withdrawalTaken = false;

  // Overloaded Constructor that uses Super
    public Certificate(int accountNumber, String accountType,
                       String firstName, String lastName,
                       String transactionDate, double transactionAmount,
                       double accountBalance, double interestEarned) {

        super(accountNumber, accountType, firstName, lastName,
              transactionDate, transactionAmount,
              accountBalance, interestEarned);
    }

    // Override processWithdrawal
   @Override
    public boolean processWithdrawal(double amount) {
        if (withdrawalTaken) {
            System.out.println(
                "Withdrawal has already been taken for this certificate."
        );
        return false;
    }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
    }

        if (amount > accountBalance) {
            System.out.println("Insufficient funds.");
            return false;
    }

        if (super.processWithdrawal(amount)) {
            withdrawalTaken = true;
            return true;
    }

        return false;
}

    // Override processInterestEarned
    @Override
    public void processInterestEarned() {
        double annualRate;
        if (!withdrawalTaken) {
            annualRate = 0.10;
        } else {
            annualRate =  0.025;
        }
        double interest = accountBalance * annualRate * 5.0 / 365.0;

        accountBalance+= interest;
        interestEarned += interest;
    }
}
    
