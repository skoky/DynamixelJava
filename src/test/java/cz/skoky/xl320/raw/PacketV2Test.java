package cz.skoky.xl320.raw;

import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.raw.PacketV2;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV2Test {


    @Test
    public void testPing() {
        byte[] ping = new PacketV2().buildPing();
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF, (byte) 0xFD,0, (byte) 0xFE,3,0,1,(byte) 0x31, (byte) 0x42},ping);
    }

    @Test
    public void testPingS1() {
        byte[] ping = new PacketV2().buildPing(1);
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF, (byte) 0xFD,0, 1,3,0,1,(byte) 0x19, (byte) 0x4E},ping);
    }


    @Test
    public void testWriteDate() {
        byte[] write = new PacketV2().buildWriteData(1, 0xc, 0x64, 0xaa);
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF,(byte) 0xFD,0,1,6,0,3,0xC,0x64, (byte) 0xaa, (byte) 0xD7, (byte) 0x38},write);
        List<PacketCommon.Data> b = new PacketV2().parse(write);

    }

    @Test
    public void parseTest() throws DecoderException {
        byte[] data = Hex.decodeHex("fffffd0001070055805e011b34c7fffffd0002070055805e011b3ef7".toCharArray());

        List<PacketV2.Data> parsed = new PacketV2().parse(data);
        assertFalse(parsed.size() != 2);
        for(PacketV2.Data d: parsed) {
            assertFalse(d.servoId>2);
            assertFalse(d.type!=PacketV2.TYPES.STATUS);
            assertFalse(d.params.length!=4);
            System.out.println(d.toString());
        }

    }

    @Test
    public void parseTestCRC() throws DecoderException {
        byte[] data = Hex.decodeHex(("fffffd0002050055805e9da8").toCharArray());
        List<PacketV2.Data> parsed = new PacketV2().parse(data);
        for(PacketV2.Data d: parsed) {
            System.out.println(d.toString());
        }

    }


}
