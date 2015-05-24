package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortException;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV1BulkScript {

    public static void main(String[] args) throws SerialPortException, InterruptedException {


        USB2Dynamixel controller = new USB2Dynamixel(SerialPort.getPort("/dev/ttyUSB0",1000000));
        controller.setVerbose();
        controller.getPort().setRecordFile("src/test/resources/audit.log");
        List<Servo> servos = controller.listServos();

        System.out.println("Servos on bus:" + servos.size());

        Iterator<Servo> servosIter = servos.iterator();

        while(servosIter.hasNext()) {
            Servo servo = servosIter.next();
            System.out.println("Servo:"+servo);
            servo.setLedOn(LedColor.BLUE);
        }

        controller.setServoList(servos);

        controller.servoList.setGoalVelocity(200);

        controller.servoList.setGoalPosition(500);
        Thread.sleep(1000);
        controller.servoList.setGoalPosition(300);

        controller.servoList.setLedOn(LedColor.OFF);

        controller.getPort().close();

    }
}
