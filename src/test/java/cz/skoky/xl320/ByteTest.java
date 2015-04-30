package cz.skoky.xl320;

import cz.skoky.xl320.keys.Register;
import cz.skoky.xl320.keys.LedColor;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by skokan on 29.4.15.
 */
public class ByteTest {

    private UsbSender mockPort;
    final ByteBuffer[] result = new ByteBuffer[1];

    @Before
    public void setUp() {
        mockPort = mock(UsbSender.class);
        when(mockPort.send(any(ByteBuffer.class))).thenAnswer(invocation -> {
            result[0] = (ByteBuffer) invocation.getArguments()[0];
            return true;
        });
    }

    @Test
    public void testCreateLEDBytes() {
        byte[] expected = new byte[]
                {1,1, (byte) Register.LED_ON_OFF.getAddress(),(byte) LedColor.BLUE.getNumber()};

        new Servo(mockPort,1).setLedOn(LedColor.BLUE);

        assertArrayEquals(result[0].array(), expected);
    }
    @Test
    public void testCreateLEDBytes2() {
        byte[] expected = new byte[]
                {1,1, (byte) Register.LED_ON_OFF.getAddress(),(byte) LedColor.OFF.getNumber()};

        new Servo(mockPort,1).setLedOn(LedColor.OFF);
        assertArrayEquals(result[0].array(), expected);
    }

    @Test
    public void testCreateModelInfoMsg() {
        byte[] expectedBytes = new byte[]
                {3,2, (byte) Register.MODEL_NUMBER.getAddress()};
        when(mockPort.waitForResponse()).thenReturn(expectedBytes);
        int model = new Servo(mockPort, 2).getModelNumber();
        assertArrayEquals("Model does no match",expectedBytes,result[0].array());
    }

}
