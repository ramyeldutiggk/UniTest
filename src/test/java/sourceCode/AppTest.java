package sourceCode;

import java.util.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class AppTest {

    static Account temp1, temp2;
    static AccountDatabase adb;
    static Transaction trn;
    static TransactionManager trnMan;
    static Timings tim;

    /*@BeforeClass
    public static void setup() {
        
    }*/

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
        System.out.println("This fucker though...");
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 1000));
    }

    @Test
    public void testTransactionManager3() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }

    @Test
    public void testTransactionManager4() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        try {
            Thread.sleep(15001);                
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
    }
    
    @Test
    public void testTransactionManager5() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10));
        try {
            Thread.sleep(15001);                
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 10000));
    }
    
   @Test
    public void TestTransectionManager6(){
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10));
    }
    
    @Test
    public void TestTransectionManager7(){
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp1.getAccountNumber(), 10));
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
}
