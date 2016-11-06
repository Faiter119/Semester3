import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by OlavH on 04-Nov-16.
 */
public class TestPacker {

    static Packer packer;
    static byte[] allBytes = new byte[]{0,1,2,3,0,1,2,3, 4,5, 0,1,2};

    @BeforeClass
    public static void init(){
        packer = new Packer(allBytes);
    }
    @Test
    public void test_generate_code(){

        byte[] compressed = new byte[]{0,1,2,3,0,0,0,0};

        byte[] search = new byte[]{0,1,2};

        byte[] code = packer.generateBackwardsCode(compressed,search, 4);

        assertEquals(-4 ,code[0]);
        assertEquals(3, code[1]);


    }
    // JUnit to the fucking rescue bless up
    @Test
    public void test_find_largest_repeat(){

        byte[] all = new byte[]{0,1,2,3,0,1,2,3,4,5, 0,1,2};
        byte[] compressed = new byte[]{0,1,2,3,0,0,0,0};

        byte[] search = new byte[]{0,1};

        assertTrue(Arrays.equals(packer.findLargestRepeatable(all, compressed, search, 4), new byte[]{0,1,2,3}));



    }

    @Test
    public void test_generate_uncompressedcode(){

        byte[] uncompressed = new byte[]{1,2,3,4,5,7,3,43,23,45,45,45,45,45,34,64,46,36,25,36,78,35,35,47,36,25,25,25,36,0,0,0,0,0,0,0,0,0};
        int uncompressedCounter = ArrayUtils.trim(uncompressed).length;

        byte[] bytes = packer.generateUncompressedArrayWithCode(uncompressed, uncompressedCounter);

        System.out.println(Arrays.toString(bytes));

    }
    @Test
    public void test_packing(){

        byte[] original = new byte[]{1,2,3,4,5, 1,2,3,4,5, 1,2,3,4,5};
        byte[] packed = new byte[]{5,1,2,3,4,5, -5,5, -7,5};

        Packer packer = new Packer(original);

        byte[] pack = packer.pack();

        System.out.println(Arrays.toString(original));
        System.out.println(Arrays.toString(pack));
        assertTrue(Arrays.equals(packed,pack));

    }
}
