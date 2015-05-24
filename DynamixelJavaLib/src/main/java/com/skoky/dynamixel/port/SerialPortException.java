package com.skoky.dynamixel.port;

/**
 * Created by skokan on 7.5.15.
 */
public class SerialPortException extends RuntimeException {
    public SerialPortException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "SerialPortException{" + getMessage() + "}";
    }
}
