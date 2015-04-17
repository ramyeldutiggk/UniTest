package sourceCode;

import java.util.*;

public class TransactionManager {

    //private int numTransactionsProcessed = 0;

    ArrayList<Timings> transactionTime = new ArrayList<Timings>();

    boolean processTransaction(int src, int dsc, long amount) {

        Transaction t1 = new Transaction(src, dsc, amount);

        int counter;
        long timeTemp = System.currentTimeMillis();
        Timings t = new Timings(src, dsc, timeTemp);
        
        if(src == dsc){
            System.out.println("HEREEEEEEEEEEEEEEEE11111111111");
            System.out.println("The source and destination accounts can't be the same.\n");
            return false;
        }

        for (counter = transactionTime.size() - 1; counter >= 0; counter--) {
            System.out.println("HEREEEEEEEEEEEEEEEE2");
            if (timeTemp - transactionTime.get(counter).getTime() > 15000) {
                System.out.println("HEREEEEEEEEEEEEEEEE3");
                transactionTime.remove(counter);
                continue;
            }

            if (transactionTime.get(counter).getSourceAccountNumber() == src || transactionTime.get(counter).getSourceAccountNumber() == dsc || transactionTime.get(counter).getDestinationAccountNumber() == dsc || transactionTime.get(counter).getDestinationAccountNumber() == src) {
                System.out.println("HEREEEEEEEEEEEEEEEE4");
                if ((timeTemp - transactionTime.get(counter).getTime()) < 15000) {
                    System.out.println("HEREEEEEEEEEEEEEEEE5");
                    System.out.println("One of the accounts being used in this transaction was already used in another transaction less than 15 seconds ago!\n");
                    System.out.println("Transaction failed!\n");
                    return false;
                }
            }
        }

        if (t1.process()) {
            System.out.println("HEREEEEEEEEEEEEEEEE6");
            transactionTime.add(t);
            System.out.println("Transaction complete!\n");
            //numTransactionsProcessed++;
            return true;
        } else {
            System.out.println("HEREEEEEEEEEEEEEEEE7");
            return false;
        }
    }
}
