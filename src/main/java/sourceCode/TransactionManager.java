package sourceCode;

public class TransactionManager {
    private int numTransactionsProcessed = 0;

    boolean processTransaction(int src, int dsc, int amount) {
        
    	Transaction t1 = new Transaction(src, dsc, amount);
        
        boolean success;
        
        if (t1.process() == true){
            success = true;
            numTransactionsProcessed++;
        } else {
            success = false;
        }
    	
    	return success;  	
    }
}
