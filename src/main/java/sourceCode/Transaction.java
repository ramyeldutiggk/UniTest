package sourceCode;

public class Transaction {

    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, long amount) {
        //Method untestable due to the non-existing return type of the method
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
}
