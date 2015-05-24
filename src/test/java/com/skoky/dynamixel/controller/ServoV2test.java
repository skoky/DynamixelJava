package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.xl320.ServoXL320;
import com.skoky.dynamixel.utils.FakeSerialPort;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by skoky on 23.5.15.
 */
public class ServoV2test {

    @Test
    public void testSyncPacketTwoBytes() throws DecoderException {

        byte[] expected = Hex.decodeHex("fffffd00fe0d00831e00020001ff0302ff032191".toCharArray());
        SerialPort port = mock(SerialPort.class);
        when(port.sendAndReceive(eq(expected))).thenReturn(new byte[]{});
        OpenCM controller = new OpenCM(port);

        controller.setServoList(Arrays.asList(new Servo[]{new ServoXL320(1, controller), new ServoXL320(2, controller)}));
        boolean done = controller.servoList.setGoalPosition(1023);
        assertFalse(!done);

    }

    @Test
    public void testSyncPacketOneBytes() throws DecoderException {

        SerialPort port = mock(SerialPort.class);
        when(port.sendAndReceive(any())).thenReturn(Hex.decodeHex("fffffd00fe0b0083190001000104020455f5".toCharArray()));
        OpenCM controller = new OpenCM(port);

        controller.setServoList(Arrays.asList(new Servo[]{new ServoXL320(1,controller),new ServoXL320(2,controller)}));
        boolean done = controller.servoList.setLedOn(LedColor.BLUE);
        assertFalse(!done);

    }

}
