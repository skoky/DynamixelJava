package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.servo.LedColor;

import java.util.Iterator;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2BulkScript {

    public static void main(String[] args) throws SerialLinkError, InterruptedException {


        OpenCM controller = new OpenCM(SerialPortFactory.get("/dev/ttyACM0"));
//        controller.setVerbose();
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

        controller.servoList.getModelNumber();
        controller.servoList.getLedOn();
        controller.servoList.setLedOn(LedColor.OFF);

        controller.getPort().close();

    }
}
