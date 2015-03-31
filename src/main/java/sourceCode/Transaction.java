package sourceCode;

import java.util.*;

public class Transaction {

    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long amount;

    ArrayList<Timings> transactionTime = new ArrayList<Timings>();

    public Transaction(int sourceAccountNumber, int destinationAccountNumber, long amount) {
        //Method untestable due to the non-existing return type of the method
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    boolean process() {

        Account source = AccountDatabase.getAccount(sourceAccountNumber);
        Account dest = AccountDatabase.getAccount(destinationAccountNumber);
        int counter;
        Date dNow = new Date();
        long timeTemp = dNow.getTime();
        Timings t = new Timings(sourceAccountNumber, destinationAccountNumber, timeTemp);

        for (counter = transactionTime.size() - 1; counter >= 0; counter--) {
            if (transactionTime.get(counter).getSourceAccountNumber() == sourceAccountNumber || transactionTime.get(counter).getSourceAccountNumber() == destinationAccountNumber || transactionTime.get(counter).getDestinationAccountNumber() == destinationAccountNumber || transactionTime.get(counter).getDestinationAccountNumber() == sourceAccountNumber) {

                if ((timeTemp - transactionTime.get(counter).getTime()) < 15000) {
                    System.out.println("One of the accounts being used in this transaction was already used in another transaction less than 15 seconds ago!\n");
                	return false;
                } else {
                    break;
                }
            }
        }

        boolean ans = (source != null && source.adjustBalance(-amount)) && (dest != null && dest.adjustBalance(amount));

        if (ans == true) {
            transactionTime.add(t);
        }

        return ans;
    }
}
