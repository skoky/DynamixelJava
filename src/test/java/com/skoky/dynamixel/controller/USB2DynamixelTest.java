package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by skokan on 14.5.15.
 */
public class USB2DynamixelTest {


    @Test
    public void testBasic() {

        SerialPort port = mock(SerialPort.class);
        when(port.sendAndReceive(any())).thenReturn(new byte[]{(byte) 0xFF, (byte) 0xFF, 1,2,3,4});
        USB2Dynamixel controller = new USB2Dynamixel(port);
        SerialPort p = controller.getPort();
        assertFalse(p==null);

        List<Servo> servos = controller.listServos();
        assertFalse(servos==null);
        assertFalse(servos.size()!=0);

        boolean done = controller.resetServos();
        assertEquals(true,done);
    }
}
