package jorje196.com.github.brewmaster;

import org.junit.Test;

import static org.junit.Assert.*;


/*
 * Created by User on 30.10.2017.
 */
public class testingStTest {
    private final int cR = 65000;
    private final int loopLim = 10;

    @Test
    public void addFunc () throws Exception {
     //   for (int i=0; i < loopLim; i++) {
            int k = (int) (Math.random() * cR);
            int j = (int) (Math.random() * cR);
            testingSt tAdd = new testingSt(k, j);
            assertEquals(j + k, tAdd.addFunc());
    //    }
    }

    @Test
    public void addFuncNeg () throws Exception {
        //   for (int i=0; i < loopLim; i++) {
        int k = (int) (Math.random() * cR);
        int j = (int) (Math.random() * cR);
        testingSt tAdd = new testingSt(k, j);
        assertEquals(j + k + 7, tAdd.addFunc());
        //    }
    }

}