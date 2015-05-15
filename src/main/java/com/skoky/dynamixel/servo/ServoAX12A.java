package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.servo.ax12a.Register;
import org.apache.commons.codec.binary.Hex;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoAX12A extends ServoCommon implements Servo {
    Logger log = Logger.getGlobal();
    private final int servoId;
    private final Controller controller;

    public ServoAX12A(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller=controller;
    }

    @Override
    public int getPresentPosition() {
        try {
            return sendReadCommand(Register.CURRENT_POSITION);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    protected void setTwoByteRegister(com.skoky.dynamixel.servo.xl320.Register cwAngleLimit, int limit) throws ResponseParsingException {

    }

    @Override
    public int getModelNumber() {
        try {
            return sendReadCommand(Register.MODEL_NUMBER);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    int getTwoByteResponse(com.skoky.dynamixel.servo.xl320.Register modelNumber) throws ResponseParsingException {
        return 0;
    }


    @Override
    public int getFirmwareVersion() {
        return 0;
    }

    @Override
    PacketCommon.Data getOneByteAnswer(com.skoky.dynamixel.servo.xl320.Register firmwareVersion) throws ResponseParsingException {
        return 0;
    }

    @Override
    public int getCWAngleLimit() {
        return 0;
    }

    private void sendWriteCommand(Register register, int value) {
        byte[] packet=null;
        if (register.getSize() == 2) {
            packet = new PacketV1().buildWriteData(servoId, register.getAddress(), value%255, value/255);
        } else if (register.getSize() == 1) {
            packet = new PacketV1().buildWriteData(servoId, register.getAddress(), value);
        }
        byte[] response = controller.getPort().sendAndReceive(packet);
        System.out.println("Response:" + Hex.encodeHexString(response));

    }

    private int sendReadCommand(Register register) throws ResponseParsingException {
        byte[] packet = new PacketV1().buildReadData(servoId, register.getAddress(), register.getSize());
        byte[] response = controller.getPort().sendAndReceive(packet);
        List<PacketV1.Data> r = new PacketV1().parse(response);
        if (r==null || r.size()==0)
            throw new ResponseParsingException("No response data");
        if (register.getSize()==1) {
            return r.get(0).params[0];
        } else if (register.getSize()==2) {
            int position = r.get(0).params[0];
            position += r.get(0).params[1]*256;
            return position;
        }
        return 0; // WTF
    }

    @Override
    public String toString() {
        return "ServoAX12A{" +
                "servoId=" + servoId +
                ", controller=" + controller +
                '}';
    }
}
