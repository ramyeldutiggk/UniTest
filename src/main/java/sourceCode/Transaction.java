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
        Account source = AccountDatabase.getAccount(sourceAccountNumber);
        Account dest = AccountDatabase.getAccount(destinationAccountNumber);
    	       
        return (source != null && source.adjustBalance(-amount)) && (dest != null && dest.adjustBalance(amount));
    }
}