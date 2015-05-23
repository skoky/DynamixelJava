package com.skoky.dynamixel.script;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortException;
import com.skoky.dynamixel.servo.ax12a.ServoAX12A;

import java.util.Arrays;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV1ScriptIDs {

    public static void main(String[] args) throws SerialPortException {


        Controller controller = new USB2Dynamixel(SerialPort.getPort("/dev/ttyUSB0", 1000000));
        SerialPort p = controller.getPort();
        p.setRecordFile("src/test/resources/audit.log");
        List<Servo> servos = controller.listServos();
        System.out.println("Servos:" + Arrays.toString(servos.toArray()));

        Servo servo = new ServoAX12A(3,controller);
        servo.setId(4);
        System.out.println("New id:" + servo.getId());


        controller.getPort().close();
    }
}
