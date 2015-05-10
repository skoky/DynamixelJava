package com.skoky.dynamixel.port;

import com.sun.jna.Platform;

/**
 * Created by skokan on 29.4.15.
 */
public class SerialPortFactory {



    public static SerialPort get(String portName) {
        if (Platform.isLinux()) {
            return new PortLinux(portName);
        }
        throw new IllegalStateException("Platform not supported");
    }

    public boolean openPort() {
        return false;
    }

    public void closePort() {
    }

    public void writeData(byte[] buf1) {

    }

    public String readResponse() {

        return null;
    }

    public static SerialPort getFirst() {
        if (Platform.isLinux()) {
            return new PortLinux(SerialPort.getPortsList().get(0));
        }

        throw new IllegalStateException("Platform not supported");

    }
}
