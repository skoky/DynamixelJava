package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.script.PingV2BulkScript;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.util.List;

import static com.skoky.dynamixel.raw.Instruction.READ;
import static com.skoky.dynamixel.raw.Instruction.WRITE;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV2Test {

    @Test
    public void testPing() {
        byte[] ping = new PacketV2().buildPacket(Instruction.PING, Packet.BROADCAST);
        assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFD, 0, (byte) 0xFE, 3, 0, 1, (byte) 0x31, (byte) 0x42}, ping);
    }

    @Test
    public void testPingS1() {
        byte[] ping = new PacketV2().buildPacket(Instruction.PING, 1);
        assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFD, 0, 1, 3, 0, 1, (byte) 0x19, (byte) 0x4E}, ping);
    }


    @Test
    public void testWriteDate() {
        byte[] write = new PacketV2().buildPacket(WRITE, 1, 0xc, 0x64, 0xaa);
        assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFD, 0, 1, 6, 0, 3, 0xC, 0x64, (byte) 0xaa, (byte) 0xD7, (byte) 0x38}, write);
        List<Data> b = new PacketV2().parse(write);

    }

    @Test
    public void parseTest() throws DecoderException {
        byte[] data = Hex.decodeHex("fffffd0001070055805e011b34c7fffffd0002070055805e011b3ef7".toCharArray());

        List<Data> parsed = new PacketV2().parse(data);
        assertFalse(parsed.size() != 2);
        for(Data d: parsed) {
            assertFalse(d.servoId>2);
            assertFalse(d.type!=PacketV2.TYPES.STATUS);
            assertFalse("Error not prsed",128!=d.error);
            assertFalse("Params number doe sot match", d.params.length!=3);
            System.out.println(d.toString());
        }

    }

    @Test
    public void parseTestCRC() throws DecoderException {
        byte[] data = Hex.decodeHex(("fffffd0002050055805e9da8").toCharArray());
        List<Data> parsed = new PacketV2().parse(data);
        for(Data d: parsed) {
            System.out.println(d.toString());
        }

    }

    @Test
    public void buildMultiRead() {
        byte[] x = new PacketV2().buildMultiPacket(READ, 1, 2, 3, 4, 5);
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xfd,0, (byte) Packet.BROADCAST,8,0, (byte) READ.getId(),1,2,3,4,5,-102,-62},x);
    }

    @Test
    public void buildMultiWrite() {
        byte[] x = new PacketV2().buildMultiPacket(WRITE, 1, 2, 3, 4, 5);
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xfd,0, (byte) Packet.BROADCAST,8,0, (byte) WRITE.getId(),1,2,3,4,5,-103,58},x);
    }

    @Test
    public void testList() {
        byte[] x = new PacketV2().buildPacket(Instruction.PING, Packet.BROADCAST);
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xfd,0, (byte) Packet.BROADCAST,3,0, (byte) Instruction.PING.getId(),49,66},x);

    }

}
