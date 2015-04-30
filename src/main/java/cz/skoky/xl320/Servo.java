package cz.skoky.xl320;

import cz.skoky.xl320.keys.Register;
import cz.skoky.xl320.keys.LedColor;

import java.nio.ByteBuffer;
import java.security.InvalidParameterException;

/**
 * Servo interface method
 * Created by skokan on 29.4.15.
 */
public class Servo {

    private final int servoId;
    private UsbSender port;

    public Servo(UsbSender port, int servoId) {
        if (servoId < 0 || servoId >4) throw new InvalidParameterException("Invalid servoId "+servoId);
        this.servoId=servoId;
        this.port=port;
    }

    public void setLedOn(LedColor color) {
        makeWriteMsg(servoId, Register.LED_ON_OFF, color.getNumber());
    }
    public int getModelNumber() {
        return makeReadMsg(servoId, Register.MODEL_NUMBER);
    }

    private int makeReadMsg(int servoId, Register register) {
        ByteBuffer bb = ByteBuffer.allocate(3);
        if (register.getSize()==1) {
            bb.put((byte)2);
        } else if (register.getSize()==2) {
            bb.put((byte)4);
        } else throw new IllegalStateException("Illegal length 2");
        bb.put((byte) servoId);
        bb.put((byte) register.getAddress());
        port.send(bb);
        byte[] response = port.waitForResponse();
        return parseResponse(response);
    }

    private int parseResponse(byte[] response) {
        return 0;
    }

    private void makeWriteMsg(int servoId, Register register, int data) {
        ByteBuffer bb;

        if (register.getSize()==1) {
            bb = ByteBuffer.allocate(4);
            bb.put((byte) 1);
        } else if (register.getSize()==2) {
            bb = ByteBuffer.allocate(5);
            bb.put((byte)3);
        } else throw new IllegalStateException("Illegal length");


        bb.put((byte)servoId);
        bb.put((byte)register.getAddress());

        if (register.getSize()==1)
            bb.put((byte)data);
        if (register.getSize()==2)
            bb.putInt(data);
        port.send(bb);
    }

}
