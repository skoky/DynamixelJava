package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortException;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Iterator;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class TrackPositionV2Script {

    public static void main(String[] args) throws InterruptedException, SerialPortException {


        OpenCM controller = new OpenCM(SerialPort.getPort("/dev/ttyACM0"));
//        controller.setVerbose();
        List<Servo> servos = controller.listServos();

        Iterator<Servo> allServos = servos.iterator();

        // init
        Servo servo;
        while(allServos.hasNext()) {
            servo = allServos.next();
            servo.setJointMode();
            System.out.println("Joint mode:" + servo.isJointMode());
            System.out.println("Wheel mode:" + servo.isWheelMode());
            servo.setMaxTorque(0);
            servo.setTorqueEnable(false);
            servo.setTorqueLimit(0);
            servo.setLedOn(LedColor.OFF);
        }

        while(true) {

            StringBuffer pos = new StringBuffer();
            allServos = servos.iterator();
            // init
            while(allServos.hasNext()) {
                servo = allServos.next();
                pos.append("id:"+servo.getId()+" position:"+servo.getPresentPosition() + "-");
            }
            System.out.println(pos.toString());
            Thread.sleep(200);
        }



        // controller.getPort().close();

    }
}
