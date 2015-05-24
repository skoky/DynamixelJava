package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.err.ErrorResponseV1Exception;
import com.skoky.dynamixel.err.ResponseParsingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1Test {

    @Test
    public void testPing() {
        byte[] ping = new PacketV1().buildPing();
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFE, 2, 1, (byte) 254}, ping);
    }
    @Test
    public void testWriteDate() {
        byte[] write = new PacketV1().buildWriteData(1, 0xc, 0x64, 0xaa);
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF,(byte) 0xFF,1,5,3,0xC,0x64, (byte) 0xaa, (byte) 0xDC},write);

    }

    @Test
    public void testReadCommand() {
        byte[] read = new PacketV1().buildReadData(1, 0xc, 0x64, 0xaa);
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF, 1, 5, 2, 0xC, 0x64, (byte) 170, (byte) 221}, read);
    }

    @Test
    public void testParse1() throws DecoderException, ResponseParsingException, ErrorResponseV1Exception {
        List<Data> data = new PacketV1().parse(Hex.decodeHex("ffff010200fc".toCharArray()));
        assertFalse(data.size() != 1);
        Data d = data.get(0);
        assertFalse(d.type!=PacketCommon.TYPES.NONE_V1);
        assertFalse(d.error!=0);
        assertFalse(d.params.length != 0);

        Data value = new PacketV1().parseFirst(Hex.decodeHex("ffff010200fc".toCharArray()));
        assertEquals(value.result, 0);

    }

    @Test
    public void testParse2() throws DecoderException, ResponseParsingException, ErrorResponseV1Exception {
        List<Data> data = new PacketV1().parse(Hex.decodeHex("ffff0104000c00ee".toCharArray()));
        assertFalse(data.size() != 1);
        Data d = data.get(0);
        assertFalse(d.type!=PacketCommon.TYPES.NONE_V1);
        assertFalse(d.error!=0);
        assertFalse(d.params.length!=2);
        assertFalse(d.params[0]==0);
        assertFalse(d.params[1]==0x0c);

        Data value = new PacketV1().parseFirst(Hex.decodeHex("ffff0104000c00ee".toCharArray()));
        assertEquals(12,value.result);

    }

    @Test
    public void testParse3() throws DecoderException, ResponseParsingException, ErrorResponseV1Exception {
        List<Data> data = new PacketV1().parse(Hex.decodeHex("ffff0104000c00ee".toCharArray()));
        assertFalse(data.size() != 1);
        Data d = data.get(0);
        assertFalse(d.type!=PacketCommon.TYPES.NONE_V1);
        assertFalse(d.error!=0);
        assertFalse(d.params.length!=2);
        assertFalse(d.params[0]==0);
        assertFalse(d.params[1]==0x0c);

        Data value = new PacketV1().parseFirst(Hex.decodeHex("ffff0104000c10de".toCharArray()));
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0x0c, 0x10});
        bb.order(ByteOrder.LITTLE_ENDIAN);
        short expected = bb.getShort();
        assertEquals(expected, value.result);

    }

    @Test
    public void testParse4() throws DecoderException, ResponseParsingException, ErrorResponseV1Exception {
        List<Data> data = new PacketV1().parse(Hex.decodeHex("ffff0104000000fa".toCharArray()));
        assertFalse(data.size() != 1);
        Data d = data.get(0);
        assertFalse(d.type!=PacketCommon.TYPES.NONE_V1);
        assertFalse(d.error!=0);
        assertFalse(d.params.length!=2);
        assertEquals(0, d.params[0]);
        assertEquals(0, d.params[1]);

        Data value = new PacketV1().parseFirst(Hex.decodeHex("ffff0104000000fa".toCharArray()));
        assertEquals(0,value.result);

    }

    @Test
    public void testParseError() throws DecoderException, ResponseParsingException {
        List<Data> data = new PacketV1().parse(Hex.decodeHex("ffff0104010000f9".toCharArray()));
        assertFalse(data.size() != 1);
        Data d = data.get(0);
        assertFalse(d.type!=PacketCommon.TYPES.NONE_V1);
        assertEquals(1, d.error);
        assertFalse(d.params.length!=2);
        assertEquals(0, d.params[0]);
        assertEquals(0, d.params[1]);

        try {
            new PacketV1().parseFirst(Hex.decodeHex("ffff0104010000f9".toCharArray()));
            assertFalse(true);
        } catch (ErrorResponseV1Exception e) {
            assertTrue(true);
        }

        try {
            new PacketV1().parseFirst(Hex.decodeHex("ffff010208f4".toCharArray()));
            assertFalse(true);
        } catch (ErrorResponseV1Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void tstCrc() throws DecoderException, ResponseParsingException {
        byte[] data = Hex.decodeHex("ffff010400fc03fb".toCharArray());
        new PacketV1().parse(data);
    }

    @Test
    public void testServoList() throws DecoderException {
        byte[] data = Hex.decodeHex("ffff020200fbffff030200faffff040200f9".toCharArray());
        try {
            List<Data> p = new PacketV1().parse(data);
            assertEquals(3, p.size());
            Data s = p.get(0);
            s.servoId=1;
            s = p.get(1);
            s.servoId=2;
            s = p.get(2);
            s.servoId=3;

        } catch (ResponseParsingException e) {
            e.printStackTrace();
        }
    }
}
