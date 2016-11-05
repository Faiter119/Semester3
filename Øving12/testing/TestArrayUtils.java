import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by OlavH on 04-Nov-16.
 */
public class TestArrayUtils {

    @Test
    public void test_index_of(){

        byte[] all = new byte[]{1,2,3,4,5,6,7,8,9};

        assertEquals(3, ArrayUtils.indexOf(all,new byte[]{4,5}));
        assertEquals(8 ,ArrayUtils.indexOf(all, new byte[]{9}));
        assertEquals(-1, ArrayUtils.indexOf(all, new byte[]{11}));
    }

    @Test
    public void test_trim(){

        byte[] all = new byte[]{1,2,3,4,5,6,0,0,0,7,8,9,0,0,0};

        assertTrue(Arrays.equals(new byte[]{1,2,3,4,5,6,7,8,9},ArrayUtils.trim(all)));


    }

}
