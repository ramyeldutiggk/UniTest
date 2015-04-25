package sourceCode;

import java.util.Scanner;

public class Account {

    Scanner sc = new Scanner(System.in);

    private int accountNumber;
    private String accountName;
    private long accountBalance;

    boolean adjustBalance(long amount) {
        long newBalance = accountBalance + amount;

        if (newBalance >= 0) {
            accountBalance = newBalance;
            return true;
        } else {
        	System.out.println("Insufficient funds!");
            return false;
        }
    }

    public Account(int accountNumber, String accountName, long accountBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(long accountBalance) {
        this.accountBalance = accountBalance;
    }
}
