package sourceCode;

import java.util.*;

public class TransactionManager {

    private int numTransactionsProcessed = 0;

    ArrayList<Timings> transactionTime = new ArrayList<Timings>();
    ArrayList<AtomicTransaction> a_TransactionsDB = new ArrayList<AtomicTransaction>();
    ArrayList<CompoundTransaction> c_TransactionsDB = new ArrayList<CompoundTransaction>();
    ArrayList<Commision> commArray = new ArrayList<Commision>();
    ArrayList<CompoundTransaction> processedTransactions = new ArrayList<CompoundTransaction>();

    public void setA_TransactionsDB(ArrayList<AtomicTransaction> a_TransactionsDB) {
        this.a_TransactionsDB = a_TransactionsDB;
    }

    public void setC_TransactionsDB(ArrayList<CompoundTransaction> c_TransactionsDB) {
        this.c_TransactionsDB = c_TransactionsDB;
    }

    boolean processTransaction(int src, int dsc, long amount) {
        Transaction t1 = new Transaction(src, dsc, amount);

        long timeTemp = System.currentTimeMillis();
        Timings t = new Timings(src, dsc, timeTemp);

        if (src == dsc) {
            System.out.println("Source and destination accounts in a transaction can't be equal.");
            return false;
        } else {

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
                numTransactionsProcessed++;
                return true;
            } else {
                System.out.println("Transaction failed!\n");
                return false;
            }
        }
    }

    public boolean addAtomicTransaction(String inputName, int acc1, int acc2, long amount) {
        if ((compoundSearch(inputName) != -1) || (atomicSearch(inputName) != -1)) {
            System.out.println("Atomic transaction with that name already exists.");
            return false;
        } else if (AccountDatabase.getAccount(acc1) == null) {
            System.out.println("Account " + acc1 + " does not exist.");
            return false;
        } else if (AccountDatabase.getAccount(acc2) == null) {
            System.out.println("Account " + acc2 + " does not exist.");
            return false;
        } else if (acc1 == acc2) {
            System.out.println("Account " + acc2 + " does not exist.");
            return false;
        } else if (amount <= 0) {
            System.out.println("The amount has to be positive");
            return false;
        } else {
            Transaction temp = new Transaction(acc1, acc2, amount);
            AtomicTransaction at = new AtomicTransaction(inputName, temp);

            this.a_TransactionsDB.add(at);

            System.out.println("Atomic transaction added");
            return true;
        }
    }

    public void editPresetAtomicTransaction(String inputName, int dest, long amount) {

        for (int counter = 0; counter < this.a_TransactionsDB.size(); counter++) {
            if (this.a_TransactionsDB.get(counter).getName().equalsIgnoreCase(inputName)) {
                this.a_TransactionsDB.get(counter).getTrn().setDestinationAccountNumber(dest);
                this.a_TransactionsDB.get(counter).getTrn().setAmount(amount);
            }
        }

    }

    public int atomicSearch(String name) {
        for (int counter = 0; counter < this.a_TransactionsDB.size(); counter++) {
            if (this.a_TransactionsDB.get(counter).getName().equalsIgnoreCase(name)) {
                return counter;
            }
        }

        return -1;
    }

    public boolean addCompoundTransaction(String inputName, ArrayList<String> children) {
        if ((compoundSearch(inputName) != -1) || (atomicSearch(inputName) != -1)) {
            System.out.println("Account with that name already exists.");
            return false;
        } else if (children.size() <= 1) {
            System.out.println("Compound transaction must have more than 1 child.");
            return false;
        } else {
            for (int counter = 0; counter < children.size(); counter++) {
                if ((this.compoundSearch(children.get(counter)) == -1) && (this.atomicSearch(children.get(counter)) == -1)) {
                    System.out.println("Transaction with title " + children.get(counter) + " does not exist!");
                    return false;
                }
            }

            CompoundTransaction ct = new CompoundTransaction(inputName, children);
            this.c_TransactionsDB.add(ct);
            return true;
        }
    }

    public int compoundSearch(String name) {
        for (int counter = 0; counter < this.c_TransactionsDB.size(); counter++) {
            if (this.c_TransactionsDB.get(counter).getName().equalsIgnoreCase(name)) {
                return counter;
            }
        }

        return -1;
    }

    public boolean processCompoundTransaction(String name) {
        int index;

        if ((index = atomicSearch(name)) != -1) {
            AtomicTransaction at = this.a_TransactionsDB.get(index);
            Transaction trn = at.getTrn();

            return this.processTransaction(trn.getSourceAccountNumber(), trn.getDestinationAccountNumber(), trn.getAmount());
        } else if ((index = compoundSearch(name)) != -1) {
            for (int counter = 0; counter < this.c_TransactionsDB.get(index).getChildren().size(); counter++) {
                if (!this.processCompoundTransaction(this.c_TransactionsDB.get(index).getChildren().get(counter))) {
                    System.out.println("Transaction having name " + this.c_TransactionsDB.get(index).getChildren().get(counter) + " failed!");
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean executePreset(String presetName, int acc1, int acc2, long amm1, long amm2) {
        if (this.compoundSearch(presetName) == -1) {
            System.out.println("This preset doesn't exist.");
            return false;
        } else if (AccountDatabase.getAccount(acc1) == null) {
            System.out.println("Deposit destination account doesn't exist.");
            return false;
        } else if (AccountDatabase.getAccount(acc2) == null) {
            System.out.println("Main destination account doesn't exist.");
            return false;
        } else if (amm1 <= 0) {
            System.out.println("Deposit ammount must be greater than 0");
            return false;
        } else if (amm2 <= 0) {
            System.out.println("Main ammount must be greater than 0");
            return false;
        }

        double percent = 0;

        for (int counter = 0; counter < this.commArray.size(); counter++) {
            if (this.commArray.get(counter).getPresetName().equalsIgnoreCase(presetName)) {
                percent = this.commArray.get(counter).getPercentage();
                break;
            }
        }

        int dest = 0;
        boolean result = false;

        for (int counter = 0; counter < this.c_TransactionsDB.size(); counter++) {
            if (this.c_TransactionsDB.get(counter).getName().equalsIgnoreCase(presetName)) {
                this.editPresetAtomicTransaction(presetName + " Deposit", acc1, amm1);
                this.editPresetAtomicTransaction(presetName + " Main", acc2, amm2);
                for (int cnt = 0; cnt < this.a_TransactionsDB.size(); cnt++) {
                    if (this.a_TransactionsDB.get(cnt).getName().equalsIgnoreCase(presetName + " Commision")) {
                        dest = this.a_TransactionsDB.get(cnt).getTrn().getDestinationAccountNumber();
                    }
                }
                this.editPresetAtomicTransaction(presetName + " Commision", dest, (long) (amm2 * (percent / 100)));
                result = processCompoundTransaction(this.c_TransactionsDB.get(counter).getName());
                if (result) {
                    this.processedTransactions.add(this.c_TransactionsDB.get(counter));
                }
                break;
            }
        }

        return result;
    }

    public boolean createPreset(String presetName, int source1, int source2, int source3, int commissionDestination, double commisionPercent) {
        //It is assumed that the custom preset keeps the same format of the High and Low risk transaction preset
        //This means that the preset will be one compound transaction made out of an atomic (deposit) and compound transaction
        //In turn this compound transaction will have two atomic transactions; main transaction and commision transaction
        //It is also assumed that just like the high/low risk transaction, the source is known for each transaction
        //The only destination account known is the one for commision

        Transaction insertTr1;
        AtomicTransaction atmTran;
        CompoundTransaction cmpTran;

        if (this.atomicSearch(presetName) != -1 || this.compoundSearch(presetName) != -1) {
            System.out.println("Preset name invalid. A transaction with that name already exists");
            return false;
        } else if (AccountDatabase.getAccount(source1) == null) {
            System.out.println("Deposit source account unavailable");
            return false;
        } else if (AccountDatabase.getAccount(source2) == null) {
            System.out.println("Main source account unavailable");
            return false;
        } else if (AccountDatabase.getAccount(source3) == null) {
            System.out.println("Commision source account unavailable");
            return false;
        } else if (AccountDatabase.getAccount(commissionDestination) == null) {
            System.out.println("Commision destination account unavailable");
            return false;
        } else if (commisionPercent < 0 || commisionPercent > 100) {
            System.out.println("Commision % must be greater than 0 and smaller or equal to 100");
            return false;
        }

        this.commArray.add(new Commision(presetName, commisionPercent));

        insertTr1 = new Transaction(source2, -1, 0);
        atmTran = new AtomicTransaction(presetName + " Main", insertTr1);
        a_TransactionsDB.add(atmTran);

        insertTr1 = new Transaction(source3, commissionDestination, 0);
        atmTran = new AtomicTransaction(presetName + " Commision", insertTr1);
        a_TransactionsDB.add(atmTran);

        ArrayList<String> presetCmp1 = new ArrayList<String>();
        presetCmp1.add(presetName + " Commision");
        presetCmp1.add(presetName + " Main");
        cmpTran = new CompoundTransaction(presetName + " Sub Transaction", presetCmp1);
        c_TransactionsDB.add(cmpTran);

        insertTr1 = new Transaction(source1, -1, 0);
        atmTran = new AtomicTransaction(presetName + " Deposit", insertTr1);
        a_TransactionsDB.add(atmTran);

        ArrayList<String> presetCmp3 = new ArrayList<String>();
        presetCmp3.add(presetName + " Sub Transaction");
        presetCmp3.add(presetName + " Deposit");
        cmpTran = new CompoundTransaction(presetName, presetCmp3);
        c_TransactionsDB.add(cmpTran);

        System.out.println("Preset added.");
        return true;
    }
    
     public ArrayList<AtomicTransaction> returnAtomic(String inputName, ArrayList<AtomicTransaction> result) {
        int index;

        if ((index = this.atomicSearch(inputName)) != -1) {
            result.add(this.a_TransactionsDB.get(index));
            return result;
        } else {
            index = this.compoundSearch(inputName);

            for (int counter = 0; counter < this.c_TransactionsDB.get(index).getChildren().size(); counter++) {
                result = returnAtomic(this.c_TransactionsDB.get(index).getChildren().get(counter), result);
            }
            return result;
        }

    }

    public ArrayList<AtomicTransaction> traverseCompoundTransaction(String transactionName, int criteria, int account) {
        if (criteria < 1 || criteria > 3) {
            System.out.println("Enter a valid criteria between 1 and 3.");
            return null;
        } else if (this.compoundSearch(transactionName) == -1) {
            System.out.println("Transaction not found.");
            return null;
        } else {
            boolean found = false;

            for (int counter = 0; counter < this.processedTransactions.size(); counter++) {
                if (this.processedTransactions.get(counter).getName().equalsIgnoreCase(transactionName)) {
                    found = true;
                }
            }

            if (found) {
                ArrayList<AtomicTransaction> atList = this.returnAtomic(transactionName, new ArrayList<AtomicTransaction>());
                ArrayList<AtomicTransaction> result = new ArrayList<AtomicTransaction>();

                switch (criteria) {
                    case 1:
                        int ind = -1;
                        long min = Long.MAX_VALUE;
                        System.out.println(Long.MAX_VALUE);

                        while (!atList.isEmpty()) {
                            ind = -1;
                            min = Long.MAX_VALUE;

                            for (int count2 = 0; count2 < atList.size(); count2++) {
                                if (atList.get(count2).getTrn().getAmount() <= min) {
                                    ind = count2;
                                    min = atList.get(count2).getTrn().getAmount();
                                }
                            }

                            result.add(atList.get(ind));
                            System.out.println(atList.get(ind).getName() + " with ammount " + atList.get(ind).getTrn().getAmount());
                            atList.remove(ind);
                        }
                        break;

                    case 2:
                        int ind2 = -1;
                        long max = Long.MIN_VALUE;

                        while (!atList.isEmpty()) {
                            ind2 = -1;
                            max = Long.MIN_VALUE;

                            for (int count2 = 0; count2 < atList.size(); count2++) {
                                if (atList.get(count2).getTrn().getAmount() >= max) {
                                    ind2 = count2;
                                    max = atList.get(count2).getTrn().getAmount();
                                }
                            }
                            result.add(atList.get(ind2));
                            System.out.println(atList.get(ind2).getName() + " with ammount " + atList.get(ind2).getTrn().getAmount());
                            atList.remove(ind2);
                        }
                        break;

                    case 3:
                        if (AccountDatabase.getAccount(account) == null) {
                            System.out.println("An account with account number " + account + " doesn't exist");
                            return null;
                        }
                        for (int counter = 0; counter < atList.size(); counter++) {
                            if (atList.get(counter).getTrn().getSourceAccountNumber() == account) {
                                System.out.println(atList.get(counter).getName());
                                result.add(atList.get(counter));
                            }
                        }
                        if (result.isEmpty()) {
                            System.out.println("There are no transactions with source account number " + account);
                            return null;
                        }
                        break;
                }
                return result;
            } else {
                return null;
            }
        }
    }
}
