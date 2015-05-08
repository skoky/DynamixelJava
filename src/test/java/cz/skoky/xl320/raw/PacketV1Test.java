package cz.skoky.xl320.raw;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1Test {


    @Test
    public void testPing() {
        byte[] ping = new PacketV1().buildPing();
        Assert.assertArrayEquals(ping,new byte[]{0,0,0,0,0});
    }
    @Test
    public void testWriteDate() {
        byte[] write = new PacketV1().buildWriteDate(1,0xc,0x64,0xaa);
        Assert.assertArrayEquals(new byte[]{(byte) 0xff,1,5,3,0xC,0x64, (byte) 0xaa, (byte) 0xDC},write);

    }
}
