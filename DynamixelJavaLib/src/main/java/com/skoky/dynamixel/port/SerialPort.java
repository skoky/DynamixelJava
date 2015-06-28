package com.skoky.dynamixel.port;

import java.util.concurrent.Future;

/**
 * Created by skokan on 7.5.15.
 */
public interface SerialPort {

    void close();
    byte[] sendAndReceive(byte[] p) throws SerialPortException;
    byte[] sendAndReceive(byte[] p, long longSleep) throws SerialPortException;

    static SerialPort getPort(String portName) throws SerialPortException {
        return new SerialPortImpl(portName);
    }
    static SerialPort getPort(String portName, int speed) throws SerialPortException {
        return new SerialPortImpl(portName, speed);
    }

    void setRecordFile(String s);

    void send(byte[] request);

    Future sendAsync(byte[] p);
}
