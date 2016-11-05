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

        byte[] code = packer.generateCode(compressed,search, 4);

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
    public void test_Lempel_Ziv_Compress(){

        byte[] result = new byte[]{0,1,2,3,-4,4,4,5,-10,3};
        assertTrue(Arrays.equals(result, packer.lempelZivCompress(allBytes)));

    }
}
