package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.PortLinux;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.Baudrate;
import com.skoky.dynamixel.servo.ServoAX12A;
import com.skoky.dynamixel.servo.ServoXL320;

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

        servo.setLedOn(true);

        servo.setMaxTorque(1023);
        System.out.println("Maxtorque:"+servo.getMaxTorque());


        int position = servo.getPresentPosition();
        System.out.println("Position:" + position);

        boolean isSet = servo.setGoalPosition(position+200);
        if (!isSet) servo.setGoalPosition(0);


        position = servo.getPresentPosition();
        System.out.println("Position:" + position);

        servo.getBaudRate();
        servo.setBaudrate(Baudrate.B1000000);

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

        servo.setId(2);
        System.out.println("New id:" + servo.getId());


        servo.setLedOn(false);
        controller.getPort().close();
    }
}
