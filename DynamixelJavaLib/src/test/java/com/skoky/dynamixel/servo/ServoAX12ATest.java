package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.servo.ax12a.ServoAX12A;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by skoky on 19.6.15.
 */
public class ServoAX12ATest {



    @Test
    public void limitsTest() throws DecoderException {

        SerialPort port = mock(SerialPort.class);
        when(port.sendAndReceive(any(byte[].class))).thenReturn(Hex.decodeHex("ffff010200fc".toCharArray()));

        Controller controller = mock(Controller.class);
        when(controller.getPort()).thenReturn(port);

        ServoAX12A s = new ServoAX12A(0, controller);
        assertEquals(true, s.setGoalPosition(10));
        try {
            s.setGoalPosition(2000);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }
}
