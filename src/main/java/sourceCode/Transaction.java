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
        boolean pSource = false;
        boolean pDestination = false;
    	
    	//database.getAccount()
    	
    	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}