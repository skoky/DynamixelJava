package com.skoky.dynamixel.port;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface SerialPort {
    List<String> getPortsList();
    void open(String portName);
    void close();

}
