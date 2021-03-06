package com.skoky.dynamixel;

import com.skoky.dynamixel.port.SerialPort;

import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public interface Controller {

    SerialPort getPort();
    List<Servo> listServos() ;
    boolean resetServos();

    boolean rebootDevice();

    void setVerbose();

    void setServoList(List<Servo> servos);
}
