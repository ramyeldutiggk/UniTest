package sourceCode;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class AppTest {
    
    Account temp;
    AccountDatabase accDB;
    Transaction trn;
    TransactionManager trnMan;

    @Before
    public void setup() {
        temp = new Account(accDB.getSize()+1,"Niki",100);
        accDB = new AccountDatabase();
        trn = new Transaction();
        trnMan = new TransactionManager();
        
        temp = new Account(accDB.getSize()+1,"Niki",100);
        
        accDB.database.add(temp);
    }

    @Test
    public void testAccount1() {
        Assert.assertEquals(true, temp.adjustBalance(1000));
    }
    
    @Test
    public void testAccount2() {
        Assert.assertEquals(false, temp.adjustBalance(-1000));
    }

    @Test
    public void testAccountDatabase1() {
        int i = 1;
        final Account amm = accDB.getAccount(i);

        Assert.assertEquals(1, amm);
    }

    @Test
    public void testAccountDatabase2() {
        final int amm = accDB.getSize();

        Assert.assertEquals(1, amm);
    }

    @Test
    public void testTransaction1() {
        final boolean amm = trn.process();

        Assert.assertEquals(true, amm);
    }

    @Test
    public void testTransactionManager1() {
        int i = 0, j = 0, k = 0;
        final boolean amm = trnMan.processTransaction(i, j, k);

        Assert.assertEquals(0, amm);
    }
}
