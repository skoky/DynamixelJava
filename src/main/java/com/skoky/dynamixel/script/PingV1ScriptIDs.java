package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.PortLinux;
import com.skoky.dynamixel.port.SerialPortFactory;

import java.util.Arrays;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV1ScriptIDs {

    public static void main(String[] args) throws SerialLinkError {


        Controller controller = new USB2Dynamixel(SerialPortFactory.get("/dev/ttyUSB0"));
        PortLinux p = (PortLinux) controller.getPort();
        p.setRecordFile("src/test/resources/audit.log");
        System.out.println("Servos:" + Arrays.toString(controller.listServos().toArray()));

//        servo.setId(2);
//        System.out.println("New id:" + servo.getId());


        controller.getPort().close();
    }
}
