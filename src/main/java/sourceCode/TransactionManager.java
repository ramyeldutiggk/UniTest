package sourceCode;

import java.util.*;

public class TransactionManager {

    private int numTransectionsProcessed = 0;

    ArrayList<Timings> transactionTime = new ArrayList<Timings>();
    ArrayList<AtomicTransaction> a_TransactionsDB = new ArrayList<AtomicTransaction>();
    ArrayList<CompoundTransaction> c_TransactionsDB = new ArrayList<CompoundTransaction>();

    boolean processTransaction(int src, int dsc, long amount) {
        Transaction t1 = new Transaction(src, dsc, amount);

        long timeTemp = System.currentTimeMillis();
        Timings t = new Timings(src, dsc, timeTemp);

        if (src == dsc) {
            System.out.println("Source and destionation accounts in a tarnsection can't be equal.");
            return false;
        } 
        else {

            for (int counter = transactionTime.size() - 1; counter >= 0; counter--) {
                timeTemp = System.currentTimeMillis();
                if (timeTemp - transactionTime.get(counter).getTime() > 15000) {
                    transactionTime.remove(counter);
                    continue;
                }

                if (transactionTime.get(counter).getSourceAccountNumber() == src || transactionTime.get(counter).getSourceAccountNumber() == dsc || transactionTime.get(counter).getDestinationAccountNumber() == src || transactionTime.get(counter).getDestinationAccountNumber() == dsc) {
                    if ((timeTemp - transactionTime.get(counter).getTime()) < 15000) {
                        counter++;
                        continue;
                    }
                }
            }

            if (t1.process()) {
                transactionTime.add(t);
                System.out.println("Transaction complete.\n");
                numTransectionsProcessed++;
                return true;
            } else {
                System.out.println("Transaction failed!\n");
                return false;
            }
        }
    }

    public boolean addAtomicTransaction(String inputName, int acc1, int acc2, long amount)
    {	
    	if((compoundSearch(inputName) != -1) || (atomicSearch(inputName) != -1))
    	{
    		System.out.println("Atomic transaction with that name already exists.");
    		return false;
    	} 
    	else if(AccountDatabase.getAccount(acc1) == null)
    	{
    		System.out.println("Account " + acc1 + " does not exist.");
    		return false;
    	} 
    	else if(AccountDatabase.getAccount(acc2) == null)
    	{
    		System.out.println("Account " + acc2 + " does not exist.");
    		return false;
    	} 
    	else if(acc1 == acc2)
    	{
    		System.out.println("Account " + acc2 + " does not exist.");
    		return false;
    	}
    	else if(amount <= 0)
    	{
    		System.out.println("The amount has to be positive");
    		return false;
    	} 
    	else 
    	{
    		Transaction temp = new Transaction(acc1, acc2, amount);
            AtomicTransaction at = new AtomicTransaction(inputName, temp);
        	
            this.a_TransactionsDB.add(at);
            
            System.out.println("Atomic transaction added");
            return true;
    	}
    }

    public int atomicSearch(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void atomicRemove(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean addCompoundTransaction(String inputName, ArrayList<String> children) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int compoundSearch(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void compoundRemove(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean processCompoundTransaction(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
