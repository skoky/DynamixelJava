package cz.skoky.xl320.raw;

import com.skoky.dynamixel.raw.PacketV1;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1Test {


    @Test
    public void testPing() {
        byte[] ping = new PacketV1().buildPing();
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF, (byte) 0xFE,2,1,(byte) 254},ping);
    }
    @Test
    public void testWriteDate() {
        byte[] write = new PacketV1().buildWriteData(1, 0xc, 0x64, 0xaa);
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF,1,5,3,0xC,0x64, (byte) 0xaa, (byte) 0xDC},write);

    }


}
