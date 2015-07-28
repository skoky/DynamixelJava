package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortException;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2BulkScript {

    public static void main(String[] args) throws SerialPortException, InterruptedException {


        OpenCM controller = new OpenCM(SerialPort.getPort("/dev/ttyACM0"),true);
//        controller.setVerbose();
        List<Servo> servos = controller.listServos();

        System.out.println("Servos on bus:" + servos.size());

        Iterator<Servo> servosIter = servos.iterator();

        controller.resetServos();
        Thread.sleep(500);

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

        controller.servoList.setGoalVelocity(200);
        Map<Integer, Integer> velos = controller.servoList.getGoalVelocity();
        velos.entrySet().stream().forEach(System.out::println);

        controller.servoList.setGoalPosition(400);
        Thread.sleep(500);
        controller.servoList.setGoalPosition(500);

        controller.servoList.setMaxTorque(450);
        Map<Integer, Integer> torques = controller.servoList.getMaxTorque();
        torques.entrySet().stream().forEach(System.out::println);

        controller.servoList.setCCWAngleLimit(999);
        Thread.sleep(200);
        Map<Integer,Integer> limits = controller.servoList.getCCWAngleLimit();
        limits.entrySet().stream().forEach(System.out::println);

        controller.servoList.setLedOn(LedColor.OFF);


        controller.getPort().close();

    }
}
