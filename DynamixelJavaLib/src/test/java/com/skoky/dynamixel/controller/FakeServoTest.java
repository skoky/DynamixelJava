package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.ax12a.ServoAX12A;
import com.skoky.dynamixel.utils.FakeSerialPort;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by skoky on 20.5.15.
 */
public class FakeServoTest {


    @Test
    public void testV1() throws IOException {

        USB2Dynamixel controller = new USB2Dynamixel(new FakeSerialPort("audit_1.log"));

        List<Servo> servos = controller.listServos();
        assertEquals(3, servos.size());

        Servo servo = new ServoAX12A(2,controller);
        assertEquals(200, servo.getMovingSpeed());
        assertEquals(36, servo.getAlarmLed());
        assertEquals(0, servo.getAlarmShutdown());
        assertEquals(1, servo.getBaudRate());
        assertFalse(servo.getCCWAngleLimit() < 10);
        assertEquals(0, servo.getCWAngleLimit());
        assertEquals(200, servo.getGoalVelocity());
        assertEquals(true, servo.setBaudrate(1));
        assertEquals(true, servo.setGoalPosition(200));

    }

    @Test
    public void testV2() throws IOException {

        USB2Dynamixel controller = new USB2Dynamixel(new FakeSerialPort("audit_2.log"));

        List<Servo> servos = controller.listServos();
        assertEquals(1, servos.size());

        Servo servo = servos.get(0);
        assertEquals(200, servo.getMovingSpeed());
        assertEquals(36, servo.getAlarmLed());
        assertEquals(0, servo.getAlarmShutdown());
        assertEquals(1, servo.getBaudRate());
        assertFalse(servo.getCCWAngleLimit() < 10);
        assertEquals(0, servo.getCWAngleLimit());
        assertEquals(200, servo.getGoalVelocity());
        assertEquals(true, servo.setBaudrate(1));
        assertEquals(true, servo.setGoalPosition(200));

    }

    @Test
    public void parsingV1() throws IOException {
        Map<String, String> responses = new FakeSerialPort("audit_1.log").getResponses();
        responses.values().stream().forEach(value -> {
            try {
                new PacketV1().parse(Hex.decodeHex(value.toCharArray()));
            } catch (ResponseParsingException e) {
                assertFalse(e.getMessage(),true);
            } catch (DecoderException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void parsingV2() throws IOException {
        Map<String, String> responses = new FakeSerialPort("audit_2.log").getResponses();
        responses.values().stream().forEach(value -> {
            try {
                new PacketV2().parse(Hex.decodeHex(value.toCharArray()));
            } catch (ResponseParsingException e) {
                assertFalse(e.getMessage() + " on data:"+value, true);
            } catch (DecoderException e) {
                e.printStackTrace();
            }
        });

    }

}
