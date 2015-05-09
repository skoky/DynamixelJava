package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketV1;

import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class openCM implements Controller {
    @Override
    public List<Servo> listServos() {
        Packet p = new PacketV1();

        p.buildPing();

        SerialPort port = SerialPortFactory.getFirst();

        byte[] response = port.sendAndReceive(p);

        p.parse(response);

        return  null;
    }
}
