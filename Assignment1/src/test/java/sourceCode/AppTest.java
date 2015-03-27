package sourceCode;

import org.junit.Test;
import org.junit.Assert;

public class AppTest 
{
   final App a = new App();
   
   @Test
    public void t1() {
        final int t1 = a.test1();
        Assert.assertEquals(0, t1);
    }
    
    @Test
    public void t2() {
        final int t2 = a.test2();
        Assert.assertEquals(1, t2);
    }
    
    @Test
    public void t3() {
        final int t3 = a.test3();
        Assert.assertEquals('a', t3); 
    }
}
