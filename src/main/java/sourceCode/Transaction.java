package sourceCode;

import java.util.*;

public class Transaction {
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;
    //long[] df;
    
    ArrayList<long[]> transactionTime = new ArrayList<long[]>();

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, long amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    boolean process() {
        Account source = AccountDatabase.getAccount(sourceAccountNumber);
        Account dest = AccountDatabase.getAccount(destinationAccountNumber);
        int counter;
        Date dNow = null;
        
        for(counter = transactionTime.size()-1; counter >=0; counter--){
            if(transactionTime.get(counter)[0] == sourceAccountNumber || transactionTime.get(counter)[0] == destinationAccountNumber || transactionTime.get(counter)[1] == destinationAccountNumber || transactionTime.get(counter)[1] == sourceAccountNumber){
                dNow = new Date();
                if((dNow.getTime() - transactionTime.get(counter)[2]) < 15000){
                    return false;
                } else {
                    break;
                }
            }
        }
        
        boolean ans = (source != null && source.adjustBalance(-amount)) && (dest != null && dest.adjustBalance(amount));
        
        if (ans == true){
            long [] tba = new long[] {sourceAccountNumber, destinationAccountNumber, dNow.getTime()};
            transactionTime.add(tba);
        }
    	       
        return ans;
    }
}