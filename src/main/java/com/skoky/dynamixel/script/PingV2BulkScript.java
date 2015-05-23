package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2BulkScript {

    public static void main(String[] args) throws SerialLinkError, InterruptedException {


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyACM0"));
        controller.setVerbose();
        List<Servo> servos = controller.listServos();

        System.out.println("Servos on bus:" + servos.size());

        Iterator<Servo> servosIter = servos.iterator();

        while(servosIter.hasNext()) {
            Servo servo = servosIter.next();
            System.out.println("Servo:"+servo);
            servo.setLedOn(LedColor.BLUE);
        }

        Thread.sleep(2000);


        controller.setServoList(servos);

        Map<Integer, Integer> models = controller.servoList.getModelNumber();
        models.entrySet().stream().forEach(System.out::println);

        Map<Integer,LedColor> leds = controller.servoList.getLedOn();
        leds.entrySet().stream().forEach(System.out::println);

        Map<Integer,Integer> positions = controller.servoList.getPresentPosition();
        positions.entrySet().stream().forEach(System.out::println);

        controller.servoList.setGoalPosition(400);
        Thread.sleep(500);
        controller.servoList.setGoalPosition(500);

        controller.servoList.setLedOn(LedColor.OFF);


        controller.getPort().close();

    }
}
