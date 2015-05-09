package cz.skoky.xl320.raw;

import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.raw.PacketV2;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV2Test {


    @Test
    public void testPing() {
        byte[] ping = new PacketV2().buildPing();
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF, (byte) 0xFD,0,3,0,1,(byte) 0xE2, (byte) 0xF0},ping);
    }
    @Test
    public void testWriteDate() {
        byte[] write = new PacketV1().buildWriteDate(1, 0xc, 0x64, 0xaa);
//        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF,1,5,3,0xC,0x64, (byte) 0xaa, (byte) 0xDC},write);

    }


}
