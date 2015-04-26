package sourceCode;

import java.util.*;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

public class AppTest {

    static Account temp1, temp2;
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
        
        insertTr1 = new Transaction(0,1,10);
        atmTran = new AtomicTransaction("Test Trn", insertTr1);
        trnMan.a_TransactionsDB.add(atmTran);
        
        insertTr1 = new Transaction(0,1,10);
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
    }

    /**************************************************************************************************
     * 
     **************************************************************************************************/
    @Test
    public void testAccount1() {
        Assert.assertEquals(true, temp1.adjustBalance(-50));
    }

    @Test
    public void testAccount2() {
        Assert.assertEquals(false, temp1.adjustBalance(-1000));
    }

    @Test
    public void testAccount3() {
        Assert.assertEquals(1, temp1.getAccountNumber());
    }

    @Test
    public void testAccount4() {
        Assert.assertThat(3, not(temp1.getAccountNumber()));
    }

    @Test
    public void testAccount5() {
        Assert.assertEquals("Niki", temp1.getAccountName());
    }

    @Test
    public void testAccount6() {
        Assert.assertThat("Malcolm", not(temp1.getAccountName()));
    }

    @Test
    public void testAccount7() {
        Assert.assertEquals(100, temp1.getAccountBalance());
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

    /**************************************************************************************************
     * 
     **************************************************************************************************/
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
        Assert.assertEquals(2, adb.getSize());
    }

    @Test
    public void testAccountDatabase5() {
        Assert.assertThat(1, not(adb.getSize()));
    }
    
    @Test
    public void testAccountDatabase6() {
        Assert.assertEquals(true, adb.addAccount(99,"TestUser",100));
    }
    
    @Test
    public void testAccountDatabase7() {
        Assert.assertEquals(false, adb.addAccount(1,"TestUser1",100));
    }
    
    @Test
    public void testAccountDatabase8() {
        Assert.assertEquals(false, adb.addAccount(100,"TestUser2",-100));
    }

    /**************************************************************************************************
     * 
     **************************************************************************************************/
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

    /**************************************************************************************************
     * 
     **************************************************************************************************/
    @Test
    public void testTransactionManager1() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 50));
    }

    @Test
    public void testTransactionManager2() {
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 1000));
    }

    /*@Test
    public void testTransactionManager3() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));

        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }*/

    @Test
    public void testTransactionManager4() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }
    
    @Test
    public void testTransactionManager5() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10000));
    }
    
   @Test
    public void TestTransectionManager6(){
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10));
    }
    
    @Test
    public void TestTransectionManager7(){
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10000));
    }

    /**************************************************************************************************
     * 
     **************************************************************************************************/
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
    
    /**************************************************************************************************
     * 
     **************************************************************************************************/
    
    @Test
    public void TestAtomicTransaction1(){
        Assert.assertEquals(true, trnMan.addAtomicTransaction("Test", temp1.getAccountNumber(), temp2.getAccountNumber(), 5));
    }
    
    @Test
    public void TestAtomicTransaction2(){
        Assert.assertEquals(2, trnMan.a_TransactionsDB.size());
    }
    
    @Test
    public void TestAtomicTransaction3(){
        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test Trn", temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }
    
    @Test
    public void TestAtomicTransaction4(){
        insertTr1 = new Transaction(5,1,10);
        atmTran = new AtomicTransaction("Test1", insertTr1);
        
        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1", 5,temp2.getAccountNumber(),10));
    }
    
    @Test
    public void TestAtomicTransaction5(){
        insertTr1 = new Transaction(0,5,10);
        atmTran = new AtomicTransaction("Test1", insertTr1);
        
        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1", temp1.getAccountNumber(),5,10));
    }
    
    @Test
    public void TestAtomicTransaction6(){
        insertTr1 = new Transaction(0,1,-10);
        atmTran = new AtomicTransaction("Test1", insertTr1);
        
        Assert.assertEquals(false, trnMan.addAtomicTransaction("Test1",temp1.getAccountNumber(),temp2.getAccountNumber(),-10));
    }
    
    @Test
    public void TestAtomicTransaction7(){
        Assert.assertEquals(0, trnMan.atomicSearch("Test Trn"));
    }
    
    @Test
    public void TestAtomicTransaction8(){
        Assert.assertEquals(0, trnMan.atomicSearch("test trn"));
    }
    
    @Test
    public void TestAtomicTransaction9(){
        Assert.assertEquals(-1, trnMan.atomicSearch("tst"));
    }
    
    @Test
    public void TestAtomicTransaction10(){
        trnMan.atomicRemove("Test Trn");
        
        Assert.assertEquals(1, trnMan.a_TransactionsDB.size());
    }
    
    @Test
    public void TestAtomicTransaction11(){
        trnMan.atomicRemove("Test Trn");
        
        Assert.assertEquals(-1, trnMan.atomicSearch("Test Trn"));
    }
    
    @Test
    public void TestAtomicTransaction12(){
        Assert.assertEquals(false, trnMan.addAtomicTransaction("CmpTran1", temp1.getAccountNumber(), temp2.getAccountNumber(), 5));
    }
    
    @Test
    public void TestAtomicTransaction13(){
        Assert.assertEquals(false, trnMan.addAtomicTransaction("tst", temp1.getAccountNumber(), temp1.getAccountNumber(), 5));
    }
    
    /**************************************************************************************************
     * 
     **************************************************************************************************/
    
    @Test
    public void TestCompoundTransaction1(){
        Assert.assertEquals(2, trnMan.c_TransactionsDB.size());
    }
    
    @Test
    public void TestCompoundTransaction2(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("Test Trn");
        al.add("Test Trn1");
        
        Assert.assertEquals(true, trnMan.addCompoundTransaction("Testing", al));
    }
    
    @Test
    public void TestCompoundTransaction3(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("CmpTrn1");
        al.add("CmpTrn2");
        
        Assert.assertEquals(true, trnMan.addCompoundTransaction("Testing", al));
    }
    
    @Test
    public void TestCompoundTransaction4(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("CmpTrn1");
        al.add("CmpTrn2");
        
        Assert.assertEquals(false, trnMan.addCompoundTransaction("CmpTran2", al));
    }
    
    @Test
    public void TestCompoundTransaction5(){
        ArrayList<String> al = new ArrayList<String>();
        
        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }
    
    @Test
    public void TestCompoundTransaction6(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("cmp");
        al.add("CmpTrn2");
        
        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }
    
    @Test
    public void TestCompoundTransaction7(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("CmpTrn1");
        al.add("cmp");
        
        Assert.assertEquals(false, trnMan.addCompoundTransaction("tst", al));
    }
    
    @Test
    public void TestCompoundTransaction8(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("CmpTrn1");
        al.add("CmpTrn2");
        al.add("Test Trn1");
        
        Assert.assertEquals(true, trnMan.addCompoundTransaction("tst", al));
    }
    
    @Test
    public void TestCompoundTransaction9(){
        Assert.assertEquals(0,trnMan.compoundSearch("CmpTran1"));
    }
    
    @Test
    public void TestCompoundTransaction10(){
        Assert.assertEquals(-1,trnMan.compoundSearch("tst"));
    }
    
    @Test
    public void TestCompoundTransaction11(){
        trnMan.compoundRemove("CmpTran1");
        Assert.assertEquals(1,trnMan.c_TransactionsDB.size());
    }
    
    @Test
    public void TestCompoundTransaction12(){
        trnMan.compoundRemove("CmpTran1");
        Assert.assertEquals(-1,trnMan.compoundSearch("CmpTran1"));
    }
    
    @Test
    public void TestCompoundTransaction13(){
        ArrayList<String> al = new ArrayList<String>();
        al.add("Test Trn");
        al.add("Test Trn1");
        Assert.assertEquals(false, trnMan.addCompoundTransaction("Test Trn", al));
    }
    
    /**************************************************************************************************
     * 
     **************************************************************************************************/
    
    @Test
    public void TestProcessCompTran1(){
        Assert.assertEquals(false, trnMan.processCompoundTransaction("tn"));
    }
    
    @Test
    public void TestProcessCompTran2(){
        Assert.assertEquals(true, trnMan.processCompoundTransaction("Test Trn"));
    }
    
    @Test
    public void TestProcessCompTran3(){
        Assert.assertEquals(true, trnMan.processCompoundTransaction("CmpTran1"));
    }
}
