package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.PortLinux;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV1Script {

    public static void main(String[] args) throws SerialLinkError {


        Controller controller = new USB2Dynamixel(SerialPortFactory.get("/dev/ttyUSB0"));

        PortLinux p = (PortLinux) controller.getPort();
        p.setRecordFile("src/test/resources/audit.log");
        System.out.println("Servos:" + Arrays.toString(controller.listServos().toArray()));

        List<Servo> servos = controller.listServos();
        if (servos.size()==0) return;
        Servo servo = servos.get(0);
        int model = servo.getModelNumber();
        System.out.println("Model number:" + model);
        System.out.println("Baudrate:" + servo.getBaudRate());

        servo.setLedOn(LedColor.WHITE);

        servo.setMaxTorque(1023);
        System.out.println("Maxtorque:"+servo.getMaxTorque());


        int position = servo.getPresentPosition();
        System.out.println("Position:" + position);

        boolean isSet = servo.setGoalPosition(position+200);
        if (!isSet) servo.setGoalPosition(0);


        position = servo.getPresentPosition();
        System.out.println("Position:" + position);

        servo.getBaudRate();
        servo.setBaudrate(1);

        System.out.println("CW limit:" + servo.getCWAngleLimit());
        servo.setCWAngleLimit(0);

        System.out.println("CCW limit:" + servo.getCCWAngleLimit());
        servo.setCCWAngleLimit(1000);

        System.out.println("Wheel mode:" + servo.isWheelMode());
        System.out.println("Joint mode:"+servo.isJointMode());
        servo.setWheelMode();
        servo.setJointMode();

        servo.setMovingSpeed(200);
        System.out.println("Moving speed:" + servo.getMovingSpeed());

        System.out.println("Temperature:"+servo.getPresentTemperature());
        System.out.println("Load:"+servo.getPresentLoad());
        System.out.println("Voltage:"+servo.getPresentVoltage());
        System.out.println("Speed:"+servo.getPresentSpeed());
        System.out.println("isMoving:"+servo.isMoving());

        servo.setTorqueLimit(999);
        System.out.println("Torque limit:" + servo.getTorqueLimit());

        servo.setReturnDelayTime(240);
        System.out.println("Return delay:" + servo.getReturnDelayTime());

        servo.setTemperatureLimit(69);
        System.out.println("Temperature limit:" + servo.getTemperatureLimit());

        boolean set = servo.setLowestLimitVoltage((float) 6);
        System.out.println("Lowest voltage limit:" + servo.getLowestLimitVoltage());
        set = servo.setHighestLimitVoltage((float)14);
        System.out.println("Highest voltage limit:" + servo.getHighestLimitVoltage());

        System.out.println("Return level:" + servo.getReturnLevel());
        System.out.println("Alarm LED:" + servo.getAlarmLed());

        servo.setLedOn(LedColor.OFF);
        controller.getPort().close();
    }
}
