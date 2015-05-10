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


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyACM0"), OpenCM.Protocols.V2);

//        List<Servo> servos = controller.listServos();

//        Servo servo = servos.get(0);
//        servo.getPresentPosition();
        Servo servo = new ServoXL320(2,controller);
        servo.getAllRegisters();
        servo.getModelNumber();


    }
}
