package jorje196.com.github.brewmaster;

import android.util.Log;

import org.junit.Test;

import static jorje196.com.github.brewmaster.BrewDbHelper.exceptCount;
import static org.junit.Assert.*;

/**
 * Created by User on 02.11.2017.
 * пока ищу ошибки инциализации
 */
public class BrewDbHelperTest {

    @Test
    public void onCreate() throws Exception {


     assertEquals(0,exceptCount);

    }

}