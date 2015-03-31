package sourceCode;

public class TransactionManager {
    private int numTransactionsProcessed = 0;

    boolean processTransaction(int src, int dsc, long amount) {
        
    	Transaction t1 = new Transaction(src, dsc, amount);
        
        boolean success;
        
        if (t1.process() == true){
            success = true;
            System.out.println("Transaction complete!\n");
            numTransactionsProcessed++;
        } else {
        	System.out.println("Transaction failed!\n");
            success = false;
        }
    	
    	return success;  	
    }
}
