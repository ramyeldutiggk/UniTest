package sourceCode;

import java.util.Scanner;

public class Account {

    Scanner sc = new Scanner(System.in);

    private int accountNumber;
    private String accountName;
    private long accountBalance;

    boolean adjustBalance(long ammount) {
        long newBalance = accountBalance + ammount;

        if (newBalance >= 0) {
            accountBalance = newBalance;
            return true;
        } else {
            return false;
        }
    }

    void addAccount() {
        boolean flag = false;
        int counter;

        do {
            System.out.println("Enter the account number\n");
            accountNumber = sc.nextInt();
            for (counter = 0; counter < AccountDatabase.getSize(); counter++) {
                flag = accountNumber != AccountDatabase.database.get(counter).getAccountNumber();
            }
        } while (flag == false);

        System.out.println("Enter the account Name\n");
        accountName = sc.next();
        System.out.println("Enter the account balance\n");
        accountBalance = sc.nextLong();

        AccountDatabase.database.add(this);
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
