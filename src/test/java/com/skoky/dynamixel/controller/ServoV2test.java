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

/**
 * Created by skoky on 23.5.15.
 */
public class ServoV2test {

    @Test
    public void testSyncPacketTwoBytes() {

        OpenCM controller = new OpenCM(new SerialPort() {
            @Override
            public void close() {

            }

            @Override
            public byte[] sendAndReceive(byte[] p) throws SerialLinkError {
                return new byte[0];
            }

            @Override
            public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialLinkError {
                System.out.println("Received:"+ Hex.encodeHexString(p));
                try {
                    assertArrayEquals("Wrong bytes", Hex.decodeHex("fffffd00fe0d00831e00020001ff0302ff032191".toCharArray()), p);
                } catch (DecoderException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        controller.setServoList(Arrays.asList(new Servo[]{new ServoXL320(1, controller), new ServoXL320(2, controller)}));
        boolean done = controller.servoList.setGoalPosition(1023);
        assertFalse(!done);


    }

    @Test
    public void testSyncPacketOneBytes() {

        OpenCM controller = new OpenCM(new SerialPort() {
            @Override
            public void close() {

            }

            @Override
            public byte[] sendAndReceive(byte[] p) throws SerialLinkError {
                return new byte[0];
            }

            @Override
            public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialLinkError {
                System.out.println("Received:"+ Hex.encodeHexString(p));
                try {
                    assertArrayEquals("Wrong bytes", Hex.decodeHex("fffffd00fe0b0083190001000104020455f5".toCharArray()), p);
                } catch (DecoderException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        controller.setServoList(Arrays.asList(new Servo[]{new ServoXL320(1,controller),new ServoXL320(2,controller)}));
        boolean done = controller.servoList.setLedOn(LedColor.BLUE);
        assertFalse(!done);

    }

}
