package sourceCode;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class AppTest {
    
    static Account temp1, temp2;
    static AccountDatabase accDB;
    static Transaction trn;
    static TransactionManager trnMan;

    @BeforeClass
    public static void setup() {
        accDB = new AccountDatabase();
        temp1 = new Account(accDB.getSize()+1,"Niki",100);
        accDB.database.add(temp1);
        temp2 = new Account(accDB.getSize()+1,"Malcolm",1000);
        accDB.database.add(temp2);
        trnMan = new TransactionManager();
        
        
    }
    
    @Before
    public void setup1(){
        temp1.setAccountBalance(100);
        temp2.setAccountBalance(1000);
    }

    @Test
    public void testAccount1() {
        Assert.assertEquals(true, temp1.adjustBalance(-50));
    }
    
    @Test
    public void testAccount2() {
        Assert.assertEquals(false, temp1.adjustBalance(-1000));
    }
    
    ///////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAccountDatabase1() {
        Assert.assertEquals(temp1, accDB.getAccount(1));
    }
    
    @Test
    public void testAccountDatabase2() {
        Assert.assertThat(temp2, not(accDB.getAccount(1)));
    }

    @Test
    public void testAccountDatabase3() {
        Assert.assertEquals(2, accDB.getSize());
    }
    
    @Test
    public void testAccountDatabase4() {
        Assert.assertThat(1, not(accDB.getSize()));
    }
    
    ///////////////////////////////////////////////////////////////////////////////

    @Test
    public void testTransaction1() {
        trn = new Transaction(temp1.getAccountNumber(),temp2.getAccountNumber(),50);
        
        Assert.assertEquals(true, trn.process());
    }
    
    @Test
    public void testTransaction2() {
        trn = new Transaction(temp1.getAccountNumber(),temp2.getAccountNumber(),1000);
        
        Assert.assertEquals(false, trn.process());
    }
    
    ///////////////////////////////////////////////////////////////////////////////

    @Test
    public void testTransactionManager1() {
        Assert.assertEquals(true, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 50));
    }
    
    @Test
    public void testTransactionManager2() {
        Assert.assertEquals(false, trnMan.processTransaction(temp1.getAccountNumber(), temp2.getAccountNumber(), 1000));
    }
}
