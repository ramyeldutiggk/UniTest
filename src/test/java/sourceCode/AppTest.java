package sourceCode;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class AppTest {
    
    Account temp1, temp2;
    AccountDatabase accDB;
    Transaction trn1, trn2;
    TransactionManager trnMan;

    @Before
    public void setup() {
        temp1 = new Account(accDB.getSize()+1,"Niki",100);
        temp2 = new Account(accDB.getSize()+1,"Malcolm",1000);
        accDB = new AccountDatabase();
        trn1 = new Transaction(temp1.getAccountNumber(),temp2.getAccountNumber(),50);
        trn2 = new Transaction(temp1.getAccountNumber(),temp2.getAccountNumber(),1000);
        trnMan = new TransactionManager();
        
        accDB.database.add(temp1);
        accDB.database.add(temp2);
    }

    @Test
    public void testAccount1() {
        Assert.assertEquals(true, temp1.adjustBalance(1000));
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
        Assert.assertEquals(true, trn1.process());
    }
    
    @Test
    public void testTransaction2() {
        Assert.assertEquals(false, trn2.process());
    }
    
    ///////////////////////////////////////////////////////////////////////////////

    @Test
    public void testTransactionManager1() {
        int i = 0, j = 0, k = 0;
        final boolean amm = trnMan.processTransaction(i, j, k);

        Assert.assertEquals(0, amm);
    }
}
