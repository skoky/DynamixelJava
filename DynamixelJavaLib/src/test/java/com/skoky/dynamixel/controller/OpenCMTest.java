package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.port.SerialPort;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by skokan on 14.5.15.
 */
public class OpenCMTest {


    @Test
    public void testOpenCM() {

        SerialPort port = mock(SerialPort.class);
        when(port.sendAndReceive(any(),anyLong())).thenReturn(new byte[]{(byte) 0xFF, (byte) 0xFF, 1, 2, 3, 4});

        OpenCM controller = new OpenCM(port);
        SerialPort p = controller.getPort();
        assertFalse(p==null);

        List<Servo> servos = controller.listServos();
        assertFalse(servos==null);
        assertFalse(servos.size()!=0);
    }


}
