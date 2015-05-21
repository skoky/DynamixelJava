package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.servo.ax12a.ServoAX12A;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by skoky on 9.5.15.
 */
public class USB2Dynamixel implements Controller {

    Logger log = Logger.getGlobal();
    private final Packet packet;
    protected final SerialPort port;

    public USB2Dynamixel(SerialPort port) {
        packet = new PacketV1();
        this.port = port;
        LogManager.getLogManager().reset();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.WARNING);
        log.setLevel(Level.INFO);
        log.addHandler(handler);
    }

    @Override
    public SerialPort getPort() {
        return port;
    }

    @Override
    public List<Servo> listServos() {

        byte[] ping = packet.buildPing();
        byte[] pingResponse = new byte[0];
        List<Servo> servos = new ArrayList<>();
        try {
            pingResponse = port.sendAndReceive(ping);
            List<Data> responses = packet.parse(pingResponse);
            if (responses != null)
                for (Data d : responses) {
                    System.out.println(d.toString());
                    servos.add(new ServoAX12A(d.servoId, this));
                }
        } catch (SerialLinkError serialLinkError) {
            serialLinkError.printStackTrace();
        } catch (ResponseParsingException e) {
            if (pingResponse==null || pingResponse.length==0 || pingResponse[0]==0)
                log.info("No servo connected to bus");
            else
                e.printStackTrace();
        }
        return servos;
    }

    @Override
    public void setVerbose() {
        log.setLevel(Level.ALL);
    }


    @Override
    public String toString() {
        return "OpenCM{" +
                ", port=" + port +
                '}';
    }
}
