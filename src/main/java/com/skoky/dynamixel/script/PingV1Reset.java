package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortException;

import java.util.Arrays;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV1Reset {

    public static void main(String[] args) throws SerialPortException {


        Controller controller = new USB2Dynamixel(SerialPort.getPort("/dev/ttyUSB0",1000000));
        SerialPort p = controller.getPort();
//        p.setRecordFile("src/test/resources/audit.log");
        System.out.println("Servos:" + Arrays.toString(controller.listServos().toArray()));

        controller.resetServos();
        controller.rebootDevice();


        controller.getPort().close();
    }
}
