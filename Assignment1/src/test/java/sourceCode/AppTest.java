package sourceCode;

import org.junit.Test;
import org.junit.Assert;

public class AppTest 
{
   final App a = new App();
   
   @Test
    public void t() {
        final int str = a.joe();

        Assert.assertEquals(0, str);

    }
}
