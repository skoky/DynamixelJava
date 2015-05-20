package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.utils.FakeSerialPort;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by skoky on 20.5.15.
 */
public class FakeServoTest {


    @Test
    public void testV1() throws IOException {

        USB2Dynamixel controller = new USB2Dynamixel(new FakeSerialPort("audit_1.log"));

        List<Servo> servos = controller.listServos();
        assertTrue(servos.size() == 1);

        Servo servo = servos.get(0);
        assertEquals(200, servo.getMovingSpeed());
        assertEquals(36, servo.getAlarmLed());
        assertEquals(0, servo.getAlarmShutdown());
        assertEquals(1, servo.getBaudRate());
        assertEquals(1023, servo.getCCWAngleLimit());
        assertEquals(0, servo.getCWAngleLimit());
        assertEquals(200, servo.getGoalVelocity());
        assertEquals(true, servo.setBaudrate(1));
        assertEquals(true, servo.setGoalPosition(200));

    }

    @Test
    public void testV2() throws IOException {

//        OpenCM controller= new OpenCM(new FakeSerialPort("audit_2.log"));
//
//        List<Servo> servos = controller.listServos();
//        assertTrue(servos.size() == 1);
    }
}
