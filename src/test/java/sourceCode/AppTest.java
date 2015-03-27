package sourceCode;

import org.junit.Test;
import org.junit.Assert;

public class AppTest 
{
   final App app = new App();
   final Account acc = new Account();
   final AccountDatabase accDB = new AccountDatabase();
   final Transaction trn = new Transaction();
   final TransactionManager trnMan = new TransactionManager();
   
   @Test
   public void testAccount1() {
        final boolean amm = acc.adjustBalance(1000);

        Assert.assertEquals(true, amm);
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
   public void testTransactionManager1(){
       int i = 0, j = 0, k = 0;
       final boolean amm = trnMan.processTransaction(i,j,k);
       
       Assert.assertEquals(0,amm);
   }
}
