package jorje196.com.github.brewmaster;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 16.03.2018.   Test методов ареометра
 */
public class AreometerAC3Test {
    @Test
    public void getAlcoholPercent(){
        assertEquals(4.55, AreometerAC3.getAlcoholPercent(28,
                9.5, 22, 1.5, 0.5), 0.005);
    }

    @Test
    public void getAlcPotential(){

        assertEquals (0, AreometerAC3.getAlcPotential(0), 0.005);
    }
    @Test
    public void getAlc() throws Exception {
        int i = 6;
        double g = 15.75;
        assertEquals(8, AreometerAC3.getAlc(i, g), 0.001);
    }
    @Test
    public void temperatureCorrection() throws Exception {
        int t;
        double g;
        t = 21;
        g = 9.5;
        assertEquals(9.5,AreometerAC3.temperatureCorrection(t,g),0.051);
    }

}