package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.ServoXL320;

import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2Script {

    public static void main(String[] args) {


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyUSB0"));

        List<Servo> servos = controller.listServos();

//        Servo servo = servos.get(0);
//        servo.getPresentPosition();
        Servo servo = new ServoXL320(2,controller);
        System.out.println("Firmware:"+servo.getFirmwareVersion());
        int model = servo.getModelNumber();
        System.out.println("Model number:" + model);

        int position = servo.getPresentPosition();
        System.out.println("Position:" + position);

            servo.setCWLimit(100);
        System.out.println("CW Limit:" + servo.getCWAngleLimit());


    }
}
