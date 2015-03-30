package sourceCode;

public class TransactionManager {
    private int numTransectionsProcessed;

    boolean processTransaction(int src, int dsc, int amount) {
        
    	Transaction t1 = new Transaction(src, dsc, amount);
    	
    	return t1.process();  	
    	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
