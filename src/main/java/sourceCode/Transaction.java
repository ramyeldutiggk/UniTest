package sourceCode;

public class Transaction {

    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;
    AccountDatabase acdb = new AccountDatabase();

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, long amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    boolean process() {
        Account source = acdb.getAccount(this.sourceAccountNumber);
        Account dest = acdb.getAccount(this.destinationAccountNumber);
        
        boolean ans = (source != null && source.adjustBalance(-amount)) && (dest != null && dest.adjustBalance(amount));

        return ans;
    }
}
