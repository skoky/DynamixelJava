package com.skoky.dynamixel.port;

import com.skoky.dynamixel.raw.Packet;
import jtermios.JTermios;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface SerialPort {
    static List<String> getPortsList() {
        return JTermios.getPortList();
    }
    void close();


    byte[] sendAndReceive(Packet p);
}
