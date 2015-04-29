package sourceCode;

import java.util.*;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

public class AppTest {

    static Account temp1, temp2, tempAcc;
    static AccountDatabase adb;
    static Transaction trn, insertTr1;
    static TransactionManager trnMan;
    static Timings tim;
    static AtomicTransaction atmTran;
    static CompoundTransaction cmpTran;

    @Before
    public void setup1() {
        adb = new AccountDatabase();
        trnMan = new TransactionManager();
        tim = new Timings(0, 0, 0);
        adb.setDatabase(new ArrayList<Account>()); //This is due to static implementation of the AccountDatabase ArrayList
        temp1 = new Account(adb.getSize() + 1, "Niki", 100);
        adb.getDatabase().add(temp1);
        temp2 = new Account(adb.getSize() + 1, "Malcolm", 1000);
        adb.getDatabase().add(temp2);

        trnMan.setA_TransactionsDB(new ArrayList<AtomicTransaction>());
        trnMan.setC_TransactionsDB(new ArrayList<CompoundTransaction>());

        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10);
        atmTran = new AtomicTransaction("Test Trn", insertTr1);
        trnMan.a_TransactionsDB.add(atmTran);

        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10);
        atmTran = new AtomicTransaction("Test Trn1", insertTr1);
        trnMan.a_TransactionsDB.add(atmTran);

        ArrayList<String> chil1 = new ArrayList<String>();
        chil1.add("Test Trn");
        chil1.add("Test Trn1");
        cmpTran = new CompoundTransaction("CmpTran1", chil1);
        trnMan.c_TransactionsDB.add(cmpTran);

        ArrayList<String> chil2 = new ArrayList<String>();
        chil2.add("Test Trn");
        chil2.add("CmpTran1");
        cmpTran = new CompoundTransaction("CmpTran2", chil2);
        trnMan.c_TransactionsDB.add(cmpTran);

        tempAcc = new Account(3123, "High Risk Deposit Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(8665, "Low Risk Deposit Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(3143, "High Risk Main Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(3133, "Low Risk Main Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(6565, "High Risk Commision Source Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(4444, "High Risk Commision Dest. Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(6588, "Low Risk Commision Source Account", 99999);
        adb.getDatabase().add(tempAcc);
        tempAcc = new Account(4445, "Low Risk Commision Dest. Account", 99999);
        adb.getDatabase().add(tempAcc);

        trnMan.createPreset("Preset High Risk", 3123, 3143, 6565, 4444, 10);
        trnMan.createPreset("Preset Low Risk", 8665, 3133, 6588, 4445, 5);
    }

    
    @Test
    public void testOrdering1() {
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("Preset High Risk", -5, 3123));
    }

    @Test
    public void testOrdering2() {
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("Preset High Risk", 5, 3123));
    }

    @Test
    public void testOrdering3() {
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("something", 1, 3123));
    }

    @Test
    public void testOrdering4() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>();
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Commision")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Deposit")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Main")));
        Assert.assertEquals(at, trnMan.traverseCompoundTransaction("Preset High Risk", 1, 3123));
    }

    @Test
    public void testOrdering5() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>();
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Main")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Deposit")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Commision")));
        Assert.assertEquals(at, trnMan.traverseCompoundTransaction("Preset High Risk", 2, 3123));
    }

    @Test
    public void testOrdering6() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>();
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Deposit")));
        Assert.assertEquals(at, trnMan.traverseCompoundTransaction("Preset High Risk", 3, 3123));
    }

    @Test
    public void testOrdering7() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("Preset High Risk", 3, 9999));
    }

    @Test
    public void testOrdering8() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("Preset High Risk", 3, temp1.getAccountNumber()));
    }
    
    @Test
    public void testOrdering9() {
        Assert.assertEquals(null, trnMan.traverseCompoundTransaction("Preset High Risk", 1, 3123));
    }
}
