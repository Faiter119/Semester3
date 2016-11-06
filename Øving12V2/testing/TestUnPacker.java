import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;/**
 * Created by Olav Husby on 05.11.2016.
 */
public class TestUnPacker {

    @Test
    public void test_find_original_length(){

        byte[] original = new byte[]{1,2,3,4, 1,2,3, 5,6,7};

        byte[] compressed = new byte[]{4,1,2,3,4, -4,3, 3,5,6,7};

        UnPacker unPacker = new UnPacker(compressed);

        assertEquals(original.length, unPacker.findOriginalLengthOf(compressed));

    }
    @Test
    public void test_unpack(){

        byte[] original = new byte[]{1,2,3,4, 1,2,3, 5,6,7};

        byte[] compressed = new byte[]{4,1,2,3,4, -4,3, 3,5,6,7};

        UnPacker unPacker = new UnPacker(compressed);

        byte[] unpacked = unPacker.unpack();
        System.out.println(Arrays.toString(unpacked));
        assertTrue(Arrays.equals(original,unpacked));

    }

}
