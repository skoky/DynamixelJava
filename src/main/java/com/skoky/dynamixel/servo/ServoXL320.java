package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.xl320.Register;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoXL320 implements Servo {
    private final int servoId;
    private final Controller controller;
    private Register twoByteRegister;

    public ServoXL320(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller= controller;
    }

    @Override
    public int getPresentPosition() {
        return getTwoByteResponse(Register.CURRENT_POSITION);
    }

    @Override
    public int getModelNumber() {
            return getTwoByteResponse(Register.MODEL_NUMBER);
    }

    @Override
    public int getFirmwareVersion() {
        return getOneByteAnswer(Register.FIRMWARE_VERSION);
    }

    @Override
    public int getCWAngleLimit() {
        return getOneByteAnswer(Register.CW_ANGLE_LIMIT);
    }

    @Override
    public void setCWLimit(int limit) {
        setTwoByteRegister(Register.CW_ANGLE_LIMIT,limit);
    }

    @Override
    public void setPresentPosition(int position) {

    }

    private int getOneByteAnswer(Register r) {
        Packet p = new PacketV2();
        int rLow = r.getAddress();
        int rHigh = r.getAddress() / 256;
        byte[] posCommand = p.buildReadData(servoId,rLow,rHigh,1,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
        if (d.size()!=1) {
            System.out.println("Invalid response");
            return -1;
        }
//        for(PacketCommon.Data x : d) {
//            System.out.println("Firmware:"+x.toString());
//        }
        if (d.get(0).params==null || d.get(0).params.length!=1) {
            System.out.println("Invalid response, params");
            return -1;
        }
        return d.get(0).params[0];
    }


    private int getTwoByteResponse(Register r) {
        Packet p = new PacketV2();
        int rLow = r.getAddress();
        int rHigh = r.getAddress()  /256;
        byte[] posCommand = p.buildReadData(servoId,rLow,rHigh,2,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
//        for(PacketCommon.Data x : d) {
//            System.out.println("Model:"+x.toString());
//        }
        if (d.size()!=1) {
            System.out.println("Invalid response");
            return -1;
        }

        if (d.get(0).params==null || d.get(0).params.length!=2) {
            System.out.println("Invalid response, params");
            return -1;
        }

        ByteBuffer b = ByteBuffer.allocate(2);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.put((byte) d.get(0).params[0]);
        b.put((byte) d.get(0).params[1]);
        return b.getShort(0);

    }

    public void setTwoByteRegister(Register r, int limit) {
        if (limit<r.getMin() || limit>r.getMax()) {
            System.out.println("Value over limits");
            return;
        }
        if (r.isReadOnly()) {
            System.out.println("Register is read-only!");
        }
        int rLow = r.getAddress();
        int rHigh = r.getAddress()  /256;
        Packet p = new PacketV2();
        byte[] posCommand = p.buildWriteData(servoId, rLow, rHigh, 12,0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        System.out.println("Write Response:"+ Hex.encodeHexString(response));
        List<PacketV2.Data> d = p.parse(response);
        System.out.println("Write Response:"+d.get(0));
    }
}
