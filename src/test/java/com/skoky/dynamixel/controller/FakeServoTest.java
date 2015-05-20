package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.utils.FakeSerialPortV1;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by skoky on 20.5.15.
 */
public class FakeServoTest {


    @Test
    public void testPing() throws IOException {

        USB2Dynamixel controller = new USB2Dynamixel(new FakeSerialPortV1());

        List<Servo> servos = controller.listServos();
        assertTrue(servos.size() == 1);

        Servo servo = servos.get(0);
        assertEquals(200, servo.getMovingSpeed());
        assertEquals(36, servo.getAlarmLed());
        assertEquals(0, servo.getAlarmShutdown());
        assertEquals(1, servo.getBaudRate());


    }
}
