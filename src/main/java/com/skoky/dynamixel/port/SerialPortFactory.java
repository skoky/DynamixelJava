package com.skoky.dynamixel.port;

import com.sun.jna.Platform;

/**
 * Created by skokan on 29.4.15.
 */
public class SerialPortFactory {



    public static void main(String [] args) {
        if (Platform.isLinux()) {
            new PortLinux("/dev/ttyACM0");
        }
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
//            PortLinux.getPortsList().get(0);
//            return new PortLinux(PortLinux.getPortsList().get(0));
            return null;
        }

        throw new IllegalStateException("Platform not supported");

    }
}
