package sourceCode;

public class Account {
    private int accountNumber;
    private String accountName;
    private long accountBalance;

    boolean adjustBalance(long account) {
        long newBalance = accountBalance-(account);
        
        if (newBalance >= 0){
            accountBalance = newBalance;
            return true;
        } else {
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
