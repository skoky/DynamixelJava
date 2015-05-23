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

/**
 * Created by skokan on 14.5.15.
 */
public class USB2DynamixelTest {


    @Test
    public void testBasic() {

        SerialPort port = new SerialPort() {
            @Override
            public void close() {}

            @Override
            public byte[] sendAndReceive(byte[] p) {
                return new byte[]{(byte) 0xFF, (byte) 0xFF, 1,2,3,4};
            }

            @Override
            public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialLinkError {
                return new byte[0];
            }

            @Override
            public void setRecordFile(String s) {

            }
        };
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
