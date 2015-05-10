package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.raw.PacketV2;

import java.util.List;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoXL320 implements Servo {
    private final int servoId;
    private final Controller controller;

    public ServoXL320(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller= controller;
    }

    @Override
    public int getPresentPosition() {
        Packet p = new PacketV2();
        byte[] posCommand = p.buildReadData(servoId,0x37,0,2,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
        return d.get(0).presentPosition;
    }

    @Override
    public int getModelNumber() {
        Packet p = new PacketV2();
        byte[] posCommand = p.buildReadData(servoId,0,0,2,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
        for(PacketCommon.Data x : d) {
            System.out.println("Model:"+x.toString());
        }

        return 1;
    }

    @Override
    public int getAllRegisters() {
        Packet p = new PacketV2();
        byte[] posCommand = p.buildReadData(servoId,3,0,1,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
        for(PacketCommon.Data x : d) {
            System.out.println("ID:"+x.toString());
        }
        return 1;

    }

}
