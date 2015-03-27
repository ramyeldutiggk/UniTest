package sourceCode;

import org.junit.Test;
import org.junit.Assert;

public class AppTest 
{
   final App a = new App();
   
   @Test
    public void t() {
        final int t1 = a.test1();
        final int t2 = a.test2();
        final int t3 = a.test3();

        Assert.assertEquals(0, t1);
        Assert.assertEquals(0, t2);
        Assert.assertEquals('a', t3); 
        Assert.assertEquals('b', t3); 
    }
}
