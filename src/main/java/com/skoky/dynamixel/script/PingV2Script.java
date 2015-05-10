package com.skoky.dynamixel.script;

import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.port.SerialPortFactory;
import com.skoky.dynamixel.raw.PacketV2;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by skoky on 9.5.15.
 */
public class PingV2Script {

    public static void main(String[] args) {

        SerialPort port = SerialPortFactory.get("/dev/ttyACM0");
        byte[] ping = new PacketV2().buildPing();
        byte[] pingResponse = port.sendAndReceive(ping);
        System.out.println("Response:"+ Hex.encodeHexString(pingResponse));
        List<PacketV2.Data> responses = new PacketV2().parse(pingResponse);
        for(PacketV2.Data d : responses) {
            System.out.println(d.toString());
        }

        port.close();

    }
}
