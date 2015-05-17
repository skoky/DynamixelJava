package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.ServoXL320;
import com.skoky.dynamixel.servo.xl320.LedColor;

import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2Script {

    public static void main(String[] args) throws SerialLinkError, InterruptedException {


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyACM0"));

        List<Servo> servos = controller.listServos();

        Servo servo = servos.get(0);
        System.out.println("Joint mode:"+servo.isJointMode());
        System.out.println("Wheel mode:"+servo.isWheelMode());

        servo.setWheelMode();
        System.out.println("Joint mode:"+servo.isJointMode());
        System.out.println("Wheel mode:"+servo.isWheelMode());

        servo.setJointMode();
        System.out.println("Joint mode:"+servo.isJointMode());
        System.out.println("Wheel mode:"+servo.isWheelMode());

        int p = servo.getPresentPosition();
        System.out.println("Position:"+p);
        int newP = p+100;
        if (newP>1000) newP=1;
        System.out.println("New Position:"+newP);
        boolean isDone = servo.setGoalPosition(newP);
        System.out.println("Done:"+isDone);
        // if (!isDone) servo.setGoalPosition(1);
        while(servo.isMoving()) {
            int pp = servo.getPresentPosition();
            int speed = servo.getMovingSpeed();
            System.out.println("Moving.... at "+pp + " speed:"+speed);
            Thread.sleep(50);
        }
        p = servo.getPresentPosition();
        System.out.println("Position P:"+p);

        p = servo.getGoalPosition();
        System.out.println("Position G:"+p);

        System.out.println("Firmware:"+servo.getFirmwareVersion());
        int model = servo.getModelNumber();
        System.out.println("Model number:" + model);

        System.out.println("CW limit:"+servo.getCWAngleLimit());
        System.out.println("CCW Limit:" + servo.getCCWAngleLimit());

        System.out.println("Temp:"+servo.getPresentTemperature());
        System.out.println("ID:" + servo.getId());
        System.out.println("Current voltage:" + servo.getPresentVoltage());
        System.out.println("Lowest voltage:" + servo.getLowestLimitVoltage());
        System.out.println("Highest voltage:" + servo.getHighestLimitVoltage());
        System.out.println("Baudrate:" + servo.getBaudRate());
        System.out.println("Delay time:" + servo.getReturnDelayTime());

        System.out.println("LED Color:" + servo.getLedOn());
        servo.setLedOn(LedColor.BLUE);
        Thread.sleep(500);
        servo.setLedOn(LedColor.YELLOW);
        Thread.sleep(500);
        servo.setLedOn(LedColor.OFF);

        controller.getPort().close();

    }
}
