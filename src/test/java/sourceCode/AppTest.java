package sourceCode;

import java.util.*;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

public class AppTest {

    //Declaration of objects
    static Account temp1, temp2, tempAcc;
    static AccountDatabase adb;
    static Transaction trn, insertTr1;
    static TransactionManager trnMan;
    static Timings tim;
    static AtomicTransaction atmTran;
    static CompoundTransaction cmpTran;

    @Before //This method is executed before each and every test
    public void setup1() {
        adb = new AccountDatabase(); //Creating new instance of account database
        trnMan = new TransactionManager(); //Creating new instance of TransactionManager
        tim = new Timings(0, 0, 0); //Creating new instance of timings
        adb.setDatabase(new ArrayList<Account>()); //This is due to static implementation of the AccountDatabase ArrayList
        temp1 = new Account(adb.getSize() + 1, "Niki", 100); //Creating a new account
        adb.getDatabase().add(temp1); //Adding the new account to the database
        temp2 = new Account(adb.getSize() + 1, "Malcolm", 1000); //Creating a second account
        adb.getDatabase().add(temp2); //Adding this second account to the database

        trnMan.setA_TransactionsDB(new ArrayList<AtomicTransaction>()); //Resetting the atomic transaction list
        trnMan.setC_TransactionsDB(new ArrayList<CompoundTransaction>()); //Reseting the compound transaction list

        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10); //Creating a new transaction
        atmTran = new AtomicTransaction("Test Trn", insertTr1); //Creating a new atomic transaction
        trnMan.a_TransactionsDB.add(atmTran); //Adding the previous atomic transaction to the list

        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10);//Creating a new transaction
        atmTran = new AtomicTransaction("Test Trn1", insertTr1);//Creating a new atomic transaction 
        trnMan.a_TransactionsDB.add(atmTran);//Adding the previous atomic transaction to the list

        ArrayList<String> chil1 = new ArrayList<String>(); //Creating a list with the children of the upcoming compound transaction
        chil1.add("Test Trn");
        chil1.add("Test Trn1");
        cmpTran = new CompoundTransaction("CmpTran1", chil1); //Creating a compound transaction with the previous list as its children
        trnMan.c_TransactionsDB.add(cmpTran); //Adding the compound transaction to the compound transaction list

        ArrayList<String> chil2 = new ArrayList<String>(); //Creating a list with the children of the upcoming compound transaction
        chil2.add("Test Trn");
        chil2.add("CmpTran1");
        cmpTran = new CompoundTransaction("CmpTran2", chil2); //Creating a compound transaction with the previous list as its children
        trnMan.c_TransactionsDB.add(cmpTran);//Adding the compound transaction to the compound transaction list
        
        //All the below add accounts to the account databse. These accounts are due to the transaction presets used in the second change
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

        trnMan.createPreset("Preset High Risk", 3123, 3143, 6565, 4444, 10); //Creating a preset
        trnMan.createPreset("Preset Low Risk", 8665, 3133, 6588, 4445, 5); //Creating another preset
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void testAccount1() { 
        Assert.assertEquals(true,, temp1.adjustBalance(-50));
    }

    @Test
    public void testAccount2() {
        Assert.assertEquals(false, temp1.adjustBalance(-1000));
    }

    @Test
    public void testAccount3() {
        Assert.assertEquals(10, temp1.getAccountNumber());
    }

    @Test
    public void testAccount4() {
        Assert.assertThat(30, not(temp1.getAccountNumber()));
    }

    @Test
    public void testAccount5() {
        Assert.assertEquals("Nikitha", temp1.getAccountName());
    }

    @Test
    public void testAccount6() {
        Assert.assertThat("Malcolm hehe", not(temp1.getAccountName()));
    }

    @Test
    public void testAccount7() {
        Assert.assertEquals(1000, temp1.getAccountBalance());
    }

    @Test
    public void testAccount8() {
        long amm = 1000;
        Assert.assertThat(amm, not(temp1.getAccountBalance()));
    }

    @Test
    public void testAccount9() {
        temp1.setAccountNumber(5);
        Assert.assertEquals(5, temp1.getAccountNumber());
    }

    @Test
    public void testAccount10() {
        temp1.setAccountNumber(5);
        Assert.assertThat(3, not(temp1.getAccountNumber()));
    }

    @Test
    public void testAccount11() {
        temp1.setAccountName("Malcolm");
        Assert.assertEquals("Malcolm", temp1.getAccountName());
    }

    @Test
    public void testAccount12() {
        temp1.setAccountName("Malcolm");
        Assert.assertThat("Niki", not(temp1.getAccountName()));
    }

    @Test
    public void testAccount13() {
        temp1.setAccountBalance(500);
        Assert.assertEquals(500, temp1.getAccountBalance());
    }

    @Test
    public void testAccount14() {
        long amm1 = 1000, amm2 = 500;
        temp1.setAccountBalance(amm1);
        Assert.assertThat(amm2, not(temp1.getAccountBalance()));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    
    @Test
    public void testAccountDatabase1() {
        Assert.assertEquals(temp1, adb.getAccount(1));
    }

    @Test
    public void testAccountDatabase2() {
        Assert.assertThat(temp2, not(adb.getAccount(1)));
    }

    @Test
    public void testAccountDatabase3() {
        Assert.assertEquals(null, adb.getAccount(50));
    }

    @Test
    public void testAccountDatabase4() {
        Assert.assertEquals(10, adb.getSize());
    }

    @Test
    public void testAccountDatabase5() {
        Assert.assertThat(1, not(adb.getSize()));
    }

    @Test
    public void testAccountDatabase6() {
        Assert.assertEquals(true, adb.addAccount(99, "TestUser", 100));
    }

    @Test
    public void testAccountDatabase7() {
        Assert.assertEquals(false, adb.addAccount(1, "TestUser1", 100));
    }

    @Test
    public void testAccountDatabase8() {
        Assert.assertEquals(false, adb.addAccount(100, "TestUser2", -100));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void testTransaction1() {
        trn = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 50);
        Assert.assertEquals(true, trn.process());
    }

    @Test
    public void testTransaction2() {
        trn = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 1000);
        Assert.assertEquals(false, trn.process());
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void testTransactionManager1() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 50));
    }

    @Test
    public void testTransactionManager2() {
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 1000));
    }

    @Test
    public void testTransactionManager3() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }

    @Test
    public void testTransactionManager4() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10000));
    }

    @Test
    public void TestTransectionManager5() {
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10));
    }

    @Test
    public void TestTransectionManager6() {
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10000));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void testTimings1() {
        tim.setSourceAccountNumber(1);
        Assert.assertEquals(1, tim.getSourceAccountNumber());
    }

    @Test
    public void testTimings2() {
        tim.setSourceAccountNumber(5);
        Assert.assertThat(1, not(tim.getSourceAccountNumber()));
    }

    @Test
    public void testTimings3() {
        tim.setDestinationAccountNumber(1);
        Assert.assertEquals(1, tim.getDestinationAccountNumber());
    }

    @Test
    public void testTimings4() {
        tim.setDestinationAccountNumber(5);
        Assert.assertThat(1, not(tim.getDestinationAccountNumber()));
    }

    @Test
    public void testTimings5() {
        Date dNow = new Date();
        long timeTemp = dNow.getTime();
        tim.setTime(timeTemp);
        Assert.assertEquals(timeTemp, tim.getTime());
    }

    @Test
    public void testTimings6() {
        Date dNow = new Date();
        long timeTemp = dNow.getTime(), test = 0;
        tim.setTime(timeTemp);
        Assert.assertThat(test, not(tim.getTime()));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void TestAtomicTransaction1() {
        Assert.assertEquals(true, trnMan.addAtomicTransaction("Test", temp1.getAccountNumber(), temp2.getAccountNumber(), 5));
    }

    @Test
    public void TestAtomicTransaction2() {
        Assert.assertEquals(8, trnMan.a_TransactionsDB.size());
    }

    @Test
    public void TestAtomicTransaction3() {
        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test Trn", temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }

    @Test
    public void TestAtomicTransaction4() {
        insertTr1 = new Transaction(5, 1, 10);//Creating a new transaction
        atmTran = new AtomicTransaction("Test1", insertTr1);//Creating an atomic transaction with the newly created transaction as a parameter

        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1", 5, temp2.getAccountNumber(), 10));//New atomic transaction added to the atomic transaction list
    }

    @Test
    public void TestAtomicTransaction5() {
        insertTr1 = new Transaction(0, 5, 10);//Creating a new transaction
        atmTran = new AtomicTransaction("Test1", insertTr1);//Creating an atomic transaction with the newly created transaction as a parameter

        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1", temp1.getAccountNumber(), 5, 10));//New atomic transaction added to the atomic transaction list
    }

    @Test
    public void TestAtomicTransaction6() {
        insertTr1 = new Transaction(0, 1, -10);//Creating a new transaction
        atmTran = new AtomicTransaction("Test1", insertTr1);//Creating an atomic transaction with the newly created transaction as a parameter

        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1", temp1.getAccountNumber(), temp2.getAccountNumber(), -10)); //New atomic transaction added to the atomic transaction list
    }

    @Test
    public void TestAtomicTransaction7() {
        Assert.assertEquals(0, trnMan.atomicSearch("Test Trn"));
    }

    @Test
    public void TestAtomicTransaction8() {
        Assert.assertEquals(0, trnMan.atomicSearch("test trn"));
    }

    @Test
    public void TestAtomicTransaction9() {
        Assert.assertEquals(-1, trnMan.atomicSearch("tst"));
    }

    @Test
    public void TestAtomicTransaction10() {
        Assert.assertEquals(false, trnMan.addAtomicTransaction("CmpTran1", temp1.getAccountNumber(), temp2.getAccountNumber(), 5));
    }

    @Test
    public void TestAtomicTransaction11() {
        Assert.assertEquals(false, trnMan.addAtomicTransaction("tst", temp1.getAccountNumber(), temp1.getAccountNumber(), 5));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void TestCompoundTransaction1() {
        Assert.assertEquals(6, trnMan.c_TransactionsDB.size());
    }

    @Test
    public void TestCompoundTransaction2() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("Test Trn");
        al.add("Test Trn1");

        Assert.assertEquals(true, trnMan.addCompoundTransaction("Testing", al));
    }

    @Test
    public void TestCompoundTransaction3() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("CmpTran1");
        al.add("CmpTran2");

        Assert.assertEquals(true, trnMan.addCompoundTransaction("Testing", al));
    }

    @Test
    public void TestCompoundTransaction4() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("CmpTrn1");
        al.add("CmpTrn2");

        Assert.assertEquals(false, trnMan.addCompoundTransaction("CmpTran2", al));
    }

    @Test
    public void TestCompoundTransaction5() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions

        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }

    @Test
    public void TestCompoundTransaction6() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("cmp");
        al.add("CmpTrn2");

        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }

    @Test
    public void TestCompoundTransaction7() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("CmpTrn1");
        al.add("cmp");

        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }

    @Test
    public void TestCompoundTransaction8() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("CmpTran1");
        al.add("CmpTran2");
        al.add("Test Trn1");

        Assert.assertEquals(true, trnMan.addCompoundTransaction("tst", al));
    }

    @Test
    public void TestCompoundTransaction9() {
        Assert.assertEquals(0, trnMan.compoundSearch("CmpTran1"));
    }

    @Test
    public void TestCompoundTransaction10() {
        Assert.assertEquals(-1, trnMan.compoundSearch("tst"));
    }

    @Test
    public void TestCompoundTransaction11() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction then adding it to the list of compound transactions
        al.add("Test Trn");
        al.add("Test Trn1");
        Assert.assertEquals(false, trnMan.addCompoundTransaction("Test Trn", al));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void TestProcessCompTran1() {
        Assert.assertEquals(false, trnMan.processCompoundTransaction("tn"));
    }

    @Test
    public void TestProcessCompTran2() {
        Assert.assertEquals(true, trnMan.processCompoundTransaction("Test Trn"));
    }

    @Test
    public void TestProcessCompTran3() {
        Assert.assertEquals(true, trnMan.processCompoundTransaction("CmpTran1"));
    }

    @Test
    public void TestProcessCompTran4() {
        Assert.assertEquals(true, trnMan.processCompoundTransaction("CmpTran2"));
    }

    @Test
    public void TestProcessCompTran5() {
        ArrayList<String> al = new ArrayList<String>();//Creating a new compound transaction using a number of transactions created previously and executing the new compound transaction
        al.add("CmpTran1");
        al.add("CmpTran2");
        trnMan.addCompoundTransaction("tst", al);

        Assert.assertEquals(true, trnMan.processCompoundTransaction("tst"));
    }

    @Test
    public void TestProcessCompTran6() {
        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 5); //Creating two new atomic transactions
        atmTran = new AtomicTransaction("tempTrnsaction0", insertTr1);
        trnMan.a_TransactionsDB.add(atmTran);

        insertTr1 = new Transaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10000);
        atmTran = new AtomicTransaction("tempTrnsaction1", insertTr1);
        trnMan.a_TransactionsDB.add(atmTran);

        ArrayList<String> al = new ArrayList<String>(); //Adding atomic transactions to the array list
        al.add("tempTrnsaction0");
        al.add("tempTrnsaction1");

        trnMan.addCompoundTransaction("tempCompTransaction1", al); //Adding the new compound transaction

        Assert.assertEquals(false, trnMan.processCompoundTransaction("tempCompTransaction1"));
    }

    @Test
    public void TestProcessCompTran7() {
        ArrayList<String> al = new ArrayList<String>(); //Creating a new compound transaction using a number of transactions created previously and executing the new compound transaction
        al.add("CmpTran1");
        al.add("CmpTran2");
        al.add("Test Trn");
        trnMan.addCompoundTransaction("tst", al);

        Assert.assertEquals(true, trnMan.processCompoundTransaction("tst"));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void TestPresets1() {
        Assert.assertEquals(true, trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 100));
    }

    @Test
    public void TestPresets2() {
        Assert.assertEquals(true, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 100));
    }

    @Test
    public void TestPresets3() {
        Assert.assertEquals(false, trnMan.executePreset("something", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 100));
    }

    @Test
    public void TestPresets4() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", 20, temp2.getAccountNumber(), 100, 100));
    }

    @Test
    public void TestPresets5() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), 20, 100, 100));
    }

    @Test
    public void TestPresets6() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), -100, 100));
    }

    @Test
    public void TestPresets7() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, -100));
    }

    @Test
    public void TestPresets8() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 999999, 100));
    }

    @Test
    public void TestPresets9() {
        Assert.assertEquals(false, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 999999));
    }

    @Test
    public void TestPresets10() {
        Assert.assertEquals(true, trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 100));
        Assert.assertEquals(true, trnMan.executePreset("Preset Low Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 100, 100));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
    @Test
    public void testNewPreset1() {
        Assert.assertEquals(false, trnMan.createPreset("Preset High Risk", 3123, 3143, 6565, 4444, 10));
    }

    @Test
    public void testNewPreset2() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 9999, 3143, 6565, 4444, 10));
    }

    @Test
    public void testNewPreset3() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 3123, 9999, 6565, 4444, 10));
    }

    @Test
    public void testNewPreset4() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 3123, 3143, 9999, 4444, 10));
    }

    @Test
    public void testNewPreset5() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 3123, 3143, 6565, 9999, 10));
    }

    @Test
    public void testNewPreset6() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 3123, 3143, 6565, 4444, 120));
    }

    @Test
    public void testNewPreset7() {
        Assert.assertEquals(false, trnMan.createPreset("new preset", 3123, 3143, 6565, 4444, -10));
    }

    /**
     * ************************************************************************************************
     *
     *************************************************************************************************
     */
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
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>(); //The strings below are the names of the processes expected. They are added in the order they are expected
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Commision")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Deposit")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Main")));
        Assert.assertEquals(at, trnMan.traverseCompoundTransaction("Preset High Risk", 1, 3123));
    }

    @Test
    public void testOrdering5() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>(); //The strings below are the names of the processes expected. They are added in the order they are expected
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Main")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Deposit")));
        at.add(trnMan.a_TransactionsDB.get(trnMan.atomicSearch("Preset High Risk Commision")));
        Assert.assertEquals(at, trnMan.traverseCompoundTransaction("Preset High Risk", 2, 3123));
    }

    @Test
    public void testOrdering6() {
        trnMan.executePreset("Preset High Risk", temp1.getAccountNumber(), temp2.getAccountNumber(), 20, 100);
        ArrayList<AtomicTransaction> at = new ArrayList<AtomicTransaction>(); //The strings below are the names of the processes expected. They are added in the order they are expected
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
