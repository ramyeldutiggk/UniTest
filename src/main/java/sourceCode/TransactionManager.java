package sourceCode;

import java.util.*;

public class TransactionManager {

    private int numTransactionsProcessed = 0;

    ArrayList<Timings> transactionTime = new ArrayList<Timings>();
    ArrayList<AtomicTransaction> a_TransactionsDB = new ArrayList<AtomicTransaction>();
    ArrayList<CompoundTransaction> c_TransactionsDB = new ArrayList<CompoundTransaction>();
    ArrayList<Commision> commArray = new ArrayList<Commision>();

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

    /*public boolean addPresetAtomicTransaction(String inputName, int acc1, int acc2, long amount) {

     Transaction temp = new Transaction(acc1, acc2, amount);
     AtomicTransaction at = new AtomicTransaction(inputName, temp);

     this.a_TransactionsDB.add(at);

     System.out.println("Atomic transaction added");
     return true;

     }*/
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

    /*public void atomicRemove(String name) {
     int index = this.atomicSearch(name);
     tomicTransaction
     if (index != -1) {
     a_TransactionsDB.remove(index);
     System.out.println("Atomic transaction deleted.");
     } else {
     System.out.println("Atomic transaction not found!");
     }
     }*/
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

    /*public void compoundRemove(String name) {
     int index = this.compoundSearch(name);

     if (index != -1) {
     c_TransactionsDB.remove(index);
     System.out.println("Compound transaction deleted.");
     } else {
     System.out.println("Compound transaction not found!");
     }
     }*/
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
        if (this.compoundSearch(presetName)==-1) {
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
        for(int counter = 0; counter < this.c_TransactionsDB.size(); counter++){
            if(this.c_TransactionsDB.get(counter).getName().equalsIgnoreCase(presetName)){
                this.editPresetAtomicTransaction(presetName + " Deposit", acc1, amm1);
                this.editPresetAtomicTransaction(presetName + " Main", acc2, amm2);
                for(int cnt = 0; cnt < this.a_TransactionsDB.size(); cnt++){
                    if(this.a_TransactionsDB.get(cnt).getName().equalsIgnoreCase(presetName + " Commision")){
                        dest = this.a_TransactionsDB.get(cnt).getTrn().getDestinationAccountNumber();
                    }
                }
                this.editPresetAtomicTransaction(presetName + " Commision", dest, (long) (amm2 * percent));
                return processCompoundTransaction(this.c_TransactionsDB.get(counter).getName());
            }
        }

        
        /*if (hl.equalsIgnoreCase("high")) {

            percent = percent / 100;
            this.editPresetAtomicTransaction("Preset High Risk Deposit", acc1, amm1);
            this.editPresetAtomicTransaction("Preset High Risk Main", acc2, amm2);
            this.editPresetAtomicTransaction("Preset High Risk Commision", 4444, (long) (amm2 * percent));

            return processCompoundTransaction("Preset High Risk");
        } else {

            this.editPresetAtomicTransaction("Preset Low Risk Deposit", acc1, amm1);
            this.editPresetAtomicTransaction("Preset Low Risk Main", acc2, amm2);
            this.editPresetAtomicTransaction("Preset Low Risk Commision", 4445, (long) (amm2 * percent));
Preset High Risk
            return processCompoundTransaction("Preset Low Risk");
        }*/
        return false;
    }

    public boolean createPreset(String presetName, int source1, int source2, int source3, int commissionDestination, double commisionPercent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
