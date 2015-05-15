package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.xl320.Register;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoXL320 extends ServoCommon implements Servo {
    Logger log = Logger.getGlobal();
    private final int servoId;
    private final Controller controller;

    public ServoXL320(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller= controller;
    }

    @Override
    public int getPresentPosition() {
        try {
            PacketCommon.Data data = sendReadCommand(Register.CURRENT_POSITION);
            return data.params[0];
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    protected void setTwoByteRegister(Register cwAngleLimit, int limit) throws ResponseParsingException {

    }

    PacketCommon.Data sendReadCommand(Register r) throws SerialLinkError, ResponseParsingException {
        Packet p = new PacketV2();
        int rLow = r.getAddress();
        int rHigh = r.getAddress() / 256;
        byte[] posCommand = p.buildReadData(servoId,rLow,rHigh,r.getSize(),0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<PacketV2.Data> d = p.parse(response);
        if (d.size()!=1)
            throw new ResponseParsingException("Invalid response");

        if (d.get(0).params==null || d.get(0).params.length!=r.getSize())
            throw new ResponseParsingException("Invalid response, params");

        return d.get(0);
    }

    public void sendWriteCommand(Register r, int limit) throws ResponseParsingException {
        if (limit < r.getMin() || limit > r.getMax()) {
            System.out.println("Value over limits");
            return;
        }
        if (r.isReadOnly()) {
            System.out.println("Register is read-only!");
        }
        int rLow = r.getAddress();
        int rHigh = r.getAddress() / 256;
        Packet p = new PacketV2();
        byte[] posCommand = new byte[0];
        if (r.getSize() == 1)
            posCommand = p.buildWriteData(servoId, rLow, rHigh, limit);
        else if (r.getSize() == 2)
            posCommand = p.buildWriteData(servoId, rLow, rHigh, limit%256,limit/256);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        log.fine("Write Response:" + Hex.encodeHexString(response));
        List<PacketV2.Data> d = p.parse(response);
        log.fine("Write Response:" + d.get(0));
    }
}
