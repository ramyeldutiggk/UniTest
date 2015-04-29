package sourceCode;

public class Transaction {

    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, long amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    boolean process() {
        Account source = AccountDatabase.getAccount(this.sourceAccountNumber);
        Account dest = AccountDatabase.getAccount(this.destinationAccountNumber);
        
        boolean ans = (source != null && source.adjustBalance(-amount)) && (dest != null && dest.adjustBalance(amount));

        return ans;
    }

    public int getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public int getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(int destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
