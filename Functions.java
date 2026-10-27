
import java.io.*;
import java.util.*;

public class Functions {

    //PRE:  accepts a string as input
    //POST: returns true if string only has digits, false otherwise
    public static boolean isDigits(String str) {
        return str.matches("\\d+");
    }

    //PRE:  accepts the ArrayList of bank accoutns & an account number
    //POST: returns true if the account exists, false otherwise
    public static boolean accountExists(ArrayList<BankAccount> accounts, int acct){
        for (BankAccount accts : accounts ){
            if (accts.getAccountNumber() == acct)
                return true;
        }
        return false;
    }

    //PRE:  accepts a string and verifies that it is valid
    //POST: if string is C, S or CD return true, otherwise return false
    public static boolean validType(String t){
        //check if the account type is valid
        if (t.equalsIgnoreCase("C") || 
        t.equalsIgnoreCase("S") || t.equalsIgnoreCase("CD"))
            return true;
        return false;
    }

    //PRE:  accepts a string and verifies that it is valid
    //POST: if string is NEW, INTEREST, DEPOSIT, TRANSFER, WITHDRAWL return true, otherwise return false
    public static boolean validTransType(String t){

        if (t.equalsIgnoreCase("NEW") || t.equalsIgnoreCase("DEPOSIT") ||
            t.equalsIgnoreCase("INTEREST") || t.equalsIgnoreCase("WITHDRAWAL") ||
            t.equalsIgnoreCase("TRANSFER"))
            return true;

        return false;
    }

    //PRE:  accepts a string and verifies that it is a valid date
    //POST: if string contains valid information, return 1, otherwise return -1
    //      can be updated to true/false
    public static int validDate(String date) {
        try {
            String[] parts = date.split("/");
            if (parts.length != 3) return -1;
            int year = Integer.parseInt(parts[2]);
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);

            if (year < 1900 || year > 2024) return -1;
            if (month < 1 || month > 12) return -1;
            if (day < 1 || day > 31) return -1;

            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void processFile(String filename, String errorLog, ArrayList<BankAccount> accounts) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            PrintWriter errWriter = new PrintWriter(new FileWriter(errorLog))
        ) {
            String line;
            int lineNumber = 0;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] tokens = line.split(",", -1);
                try {
                    if (tokens.length != 7) {
                        throw new IllegalArgumentException("Incorrect number of fields in record: " + line);
                    }
                    for (int i = 0; i < tokens.length; i++) {
                        tokens[i] = tokens[i].trim();
                    }
                  
                    //verify the tokens ensuring that data read is valid for the transaction before processing the command
                    String date = tokens[4];
                    String transType = tokens[5].toUpperCase();
                    String[] t = tokens;
                    if (validDate(date) == -1) {
                        throw new IllegalArgumentException("Invalid date in record: " + line);
                    }
                    if (!validTransType(transType)) {
                        throw new IllegalArgumentException("Invalid transaction type in record: " + line);
                    }
                    // INTEREST: Apply interest to every account.
                if (transType.equals("INTEREST")) {
                    if (!t[0].isEmpty() || !t[1].isEmpty()
                            || !t[2].isEmpty() || !t[3].isEmpty()
                            || !t[6].isEmpty()) {
                        throw new IllegalArgumentException(
                            "Invalid INTEREST record."
                        );
                    }

                    for (BankAccount acct : accounts) {
                        acct.processInterestEarned();
                    }
                    continue;
                }

                if (t[0].isEmpty()) {
                    throw new IllegalArgumentException(
                        "Missing account number."
                    );
                }

                if (!isDigits(t[0])) {
                    throw new IllegalArgumentException(
                        "Account number must contain digits."
                    );
                }

                int acctNum = Integer.parseInt(t[0]);

                // TRANSFER: column 2 contains destination account.
                if (transType.equals("TRANSFER")) {
                    if (t[1].isEmpty() || !isDigits(t[1])) {
                        throw new IllegalArgumentException(
                            "Invalid destination account number."
                        );
                    }

                    if (!t[2].isEmpty() || !t[3].isEmpty()) {
                        throw new IllegalArgumentException(
                            "Transfer names must be blank."
                        );
                    }

                    if (t[6].isEmpty()) {
                        throw new IllegalArgumentException(
                            "Missing transfer amount."
                        );
                    }

                    int destNum = Integer.parseInt(t[1]);
                    double amount = Double.parseDouble(t[6]);

                    if (amount <= 0) {
                        throw new IllegalArgumentException(
                            "Transfer amount must be positive."
                        );
                    }

                    BankAccount source = null;
                    BankAccount destination = null;

                    for (BankAccount acct : accounts) {
                        if (acct.getAccountNumber() == acctNum) {
                            source = acct;
                        }
                        if (acct.getAccountNumber() == destNum) {
                            destination = acct;
                        }
                    }

                    if (source == null || destination == null) {
                        throw new IllegalArgumentException(
                            "Source or destination account not found."
                        );
                    }

                    if (source == destination) {
                        throw new IllegalArgumentException(
                            "Cannot transfer to the same account."
                        );
                    }

                    if (amount > source.getAccountBalance()) {
                        throw new IllegalArgumentException(
                            "Insufficient funds for transfer."
                        );
                    }

                    source.processWithdrawal(amount);
                    destination.processDeposit(amount);
                    continue;
                }

                if (t[1].isEmpty()) {
                    throw new IllegalArgumentException(
                        "Missing account type."
                    );
                }

                String type = t[1].toUpperCase();
                String firstName = t[2];
                String lastName = t[3];

                if (!validType(type)) {
                    throw new IllegalArgumentException(
                        "Invalid account type."
                    );
                }

                if (firstName.isEmpty() || lastName.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Missing account holder name."
                    );
                }

                if (t[6].isEmpty()) {
                    throw new IllegalArgumentException(
                        "Missing transaction amount."
                    );
                }

                double amount = Double.parseDouble(t[6]);

                if (amount < 0) {
                    throw new IllegalArgumentException(
                        "Amount must be positive."
                    );
                }

                // NEW: Create an account.
                if (transType.equals("NEW")) {
                    if (accountExists(accounts, acctNum)) {
                        throw new IllegalArgumentException(
                            "Account already exists."
                        );
                    }

                    BankAccount newAccount;
                    if (type.equalsIgnoreCase("CD")) {
                        newAccount = new Certificate(
                            acctNum, type, firstName, lastName,
                            date, (double) amount, amount, 0
                        );
                    } else if (type.equalsIgnoreCase("C")){
                        newAccount = new Checking(
                            acctNum, type, firstName, lastName,
                            date, (double) amount, amount, 0
                        );
                    } else {
                        newAccount = new Savings(
                            acctNum, type, firstName, lastName,
                            date, (double) amount, amount, 0
                        );
                    }
                    accounts.add(newAccount);
                    continue;
                }

                // All other transactions require an existing account.
                BankAccount account = null;

                for (BankAccount acct : accounts) {
                    if (acct.getAccountNumber() == acctNum) {
                        account = acct;
                        break;
                    }
                }

                if (account == null) {
                    throw new IllegalArgumentException(
                        "Account does not exist."
                    );
                }

                // Ensure the transaction account type matches.
                if (!account.getAccountType().equalsIgnoreCase(type)) {
                    throw new IllegalArgumentException(
                        "Account type does not match."
                    );
                }

                switch (transType) {
                    case "DEPOSIT":
                        account.processDeposit(amount);
                        break;

                    case "WITHDRAWAL":
                        if (!account.processWithdrawal(amount)) {
                            throw new IllegalArgumentException(
                                "Withdrawal failed for account " + acctNum
                            );
                        }
                        break;

                    default:
                        throw new IllegalArgumentException(
                            "Unsupported transaction."
                        );
                }
                } catch (IllegalArgumentException e) {
                    errWriter.println("Line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }

    //PRE:  none
    //POST: sorts information in accounts by account number, prints bank account information in accounts to report file
    public static void printReport(ArrayList<BankAccount> accounts, String reportFile) {
        accounts.sort(Comparator.comparing(BankAccount::getAccountNumber));
        try (PrintWriter out = new PrintWriter(new FileWriter(reportFile))) {
            out.printf("%-20s%-20s%-20s%-15s%10s%15s%n", "Account#", "Type", "First Name", "Last Name", "Balance", "Interest");
            for (BankAccount acct : accounts) {
                acct.printDetails(out);
            }
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
}
