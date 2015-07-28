package com.skoky.dynamixel.port;

import purejavacomm.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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

    static List<String> listAvailablePorts() {
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        List<String> r = new ArrayList<String>();
        while(e.hasMoreElements()) {
            CommPortIdentifier p = (CommPortIdentifier) e.nextElement();
            r.add(p.getName());
        }
        return r;
    }

}
