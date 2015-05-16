package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.servo.xl320.Register;

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
            Data data = sendReadCommand(Register.CURRENT_POSITION);
            return data.params[0];
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }



//    PacketCommon.Data sendReadCommand(Register r) throws SerialLinkError, ResponseParsingException {
//        Packet p = new PacketV2();
//        int rLow = r.getAddress();
//        int rHigh = r.getAddress() / 256;
//        byte[] posCommand = p.buildReadData(servoId,rLow,rHigh,r.getSize(),0);
//        byte[] response = controller.getPort().sendAndReceive(posCommand);
//        List<PacketV2.Data> d = p.parse(response);
//        if (d.size()!=1)
//            throw new ResponseParsingException("Invalid response");
//
//        if (d.get(0).params==null || d.get(0).params.length!=r.getSize())
//            throw new ResponseParsingException("Invalid response, params");
//
//        return d.get(0);
//    }

    Data sendReadCommand(Register r) throws SerialLinkError, ResponseParsingException {
        return null;
    }

    Data sendWriteCommand(Register r) throws SerialLinkError, ResponseParsingException {
        return null;
    }


/*
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
*/


    @Override
    public int getPresentSpeed() {
        try {
            Data data = sendReadCommand(Register.PRESENT_SPEED);
            return data.params[0]+data.params[1]*255;
        } catch (ResponseParsingException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getPresentLoad() {
        return -1;

    }

    @Override
    public int getPresentVoltage() {
        return 0;
    }

    @Override
    public int getPresentTemperature() {
        return 0;
    }

    @Override
    public int getRegistered() {
        return 0;
    }

    @Override
    public int isMoving() {
        return 0;
    }

    @Override
    public void setEEPROMLock(boolean locked) {

    }

    @Override
    public boolean getEEPROMLock() {
        return false;
    }

    @Override
    public void setPunch(int punch) {

    }

    @Override
    public int getPunch() {
        return 0;
    }

    @Override
    public int getModelNumber() {
        try {
            Data data = sendReadCommand(Register.MODEL_NUMBER);
            return data.params[0] + data.params[1]*256;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getFirmwareVersion() {
        try {
            Data data = sendReadCommand(Register.FIRMWARE_VERSION);
            return data.params[0];
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int newId) {

    }

    @Override
    public Baudrate getBaudRate() {
        return null;
    }

    @Override
    public void setBaudrate(Baudrate b) {

    }

    @Override
    public void setReturnDelayTime(int time) {

    }

    @Override
    public int getReturnDelayTime() {
        return 0;
    }

    @Override
    public void setCWAngleLimit(int limit) {

    }

    @Override
    public int getCWAngleLimit() {
        try {
            Data data = sendReadCommand(Register.CW_ANGLE_LIMIT);
            return data.params[0];
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public void setCCWAngleLimit(int limit) {

    }

    @Override
    public int getCCWAngleLimit() {
        return 0;
    }

    @Override
    public void setTemperatureLimit(int limit) {

    }

    @Override
    public int getTemperatureLimit() {
        return 0;
    }

    @Override
    public void setLowestLimitVoltage(int limit) {

    }

    @Override
    public int getLowestLimitVoltage() {
        return 0;
    }

    @Override
    public void setHighestLimitVoltage(int limit) {

    }

    @Override
    public int getHifgestLimitVoltage() {
        return 0;
    }

    @Override
    public boolean setMaxTorque(int torqueLimit) {
        return false;
    }

    @Override
    public int getMaxTorque() {
        return 0;
    }

    @Override
    public void setReturnLevel(ReturnLevel level) {

    }

    @Override
    public ReturnLevel getReturnLevel() {
        return null;
    }

    @Override
    public void setAlarmLed(boolean onOff) {

    }

    @Override
    public boolean getAlarmLed() {
        return false;
    }

    @Override
    public void setAlarmShutdown(int value) {

    }

    @Override
    public int getAlarmShutdown() {
        return 0;
    }

    @Override
    public void setTorqueEnable(boolean enable) {

    }

    @Override
    public boolean getTorqueEnabled() {
        return false;
    }

    @Override
    public boolean setLedOn(boolean on) {
        return false;
    }

    @Override
    public boolean getLedOn() {
        return false;
    }

    @Override
    public void setGoalPosition(int position) {

    }

    @Override
    public int getGoalPosition() {
        return 0;
    }

    @Override
    public void setMovingSpeed(int speed) {

    }

    @Override
    public int getMovingSpeed() {
        return 0;
    }

    @Override
    public void setTorqueLimit(int limit) {

    }

    @Override
    public int getTorqueLimit() {
        return 0;
    }



}
