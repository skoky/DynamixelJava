package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2Script {

    public static void main(String[] args) throws SerialLinkError, InterruptedException {


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyACM0"));
//        controller.setVerbose();
        List<Servo> servos = controller.listServos();

        System.out.println("Servos on bus:" + servos.size());

        Iterator<Servo> servosIter = servos.iterator();

        while(servosIter.hasNext()) {
            Servo servo = servosIter.next();
            System.out.println("Joint mode:" + servo.isJointMode());
            System.out.println("Wheel mode:" + servo.isWheelMode());

            servo.setWheelMode();
            System.out.println("Joint mode:" + servo.isJointMode());
            System.out.println("Wheel mode:" + servo.isWheelMode());

            servo.setJointMode();
            System.out.println("Joint mode:" + servo.isJointMode());
            System.out.println("Wheel mode:" + servo.isWheelMode());

            int p = servo.getPresentPosition();
            System.out.println("Position:" + p);
            int newP = p + 100;
            if (newP > 1000) newP = 1;
            System.out.println("New Position:" + newP);
            boolean isDone = servo.setGoalPosition(newP);
            System.out.println("Done:" + isDone);
            // if (!isDone) servo.setGoalPosition(1);
            while (servo.isMoving()) {
                int pp = servo.getPresentPosition();
                int speed = servo.getMovingSpeed();
                System.out.println("Moving.... at " + pp + " speed:" + speed);
                Thread.sleep(50);
            }
            p = servo.getPresentPosition();
            System.out.println("Position P:" + p);

            servo.setGoalPositionAndWait(newP-100);

            p = servo.getGoalPosition();
            System.out.println("Position G:" + p);

            System.out.println("Firmware:" + servo.getFirmwareVersion());
            int model = servo.getModelNumber();
            System.out.println("Model number:" + model);

            servo.setCWAngleLimit(1);
            System.out.println("CW limit:" + servo.getCWAngleLimit());
            servo.setCCWAngleLimit(1022);
            System.out.println("CCW Limit:" + servo.getCCWAngleLimit());

            System.out.println("Temp:" + servo.getPresentTemperature());
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

            System.out.println("Temperature limit:" + servo.getTemperatureLimit());
            servo.setMaxTorque(1022);
            System.out.println("Max torque:" + servo.getMaxTorque());
            System.out.println("Return level:" + servo.getReturnLevel());

            servo.setGoalVelocity(500);
            System.out.println("Goal velocity:" + servo.getGoalVelocity());

            servo.setTorqueLimit(1022);
            System.out.println("Torque limit:" + servo.getTorqueLimit());
            System.out.println("Present load:" + servo.getPresentLoad());
            System.out.println("HW error:" + servo.getHWStatusError());
            servo.setAlarmShutdown(0);
            System.out.println("Alarm shutdown:" + servo.getAlarmShutdown());

        }
        controller.getPort().close();

    }
}
