package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ErrorResponseException;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.servo.ax12a.Register;


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


    public int getPresentPosition() {
        try {
            return sendReadCommand(Register.CURRENT_POSITION);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }


    public void setPresentPosition(int position) {
        //sendWriteCommand(Register.GOAL_POSITION, position);
    }



    @Override
    public String toString() {
        return "ServoAX12A{" +
                "servoId=" + servoId +
                ", controller=" + controller +
                '}';
    }


    @Override
    public int getPresentSpeed() {
        try {
            return sendReadCommand(Register.PRESENT_SPEED);
        } catch (ResponseParsingException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getPresentLoad() {
        return 0;
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
            return sendReadCommand(Register.MODEL_NUMBER);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getFirmwareVersion() {
        try {
            return  sendReadCommand(Register.FIRMWARE_VERSION);
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
            return sendReadCommand(Register.CW_ANGLE_LIMIT);
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
        return sendWriteCommandNoEx(Register.MAX_TORQUE,torqueLimit);
    }


    @Override
    public int getMaxTorque() {
        try {
            return sendReadCommand(Register.MAX_TORQUE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
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
        if (on)
            return sendWriteCommandNoEx(Register.LED_ON_OFF,1);
        else
            return sendWriteCommandNoEx(Register.LED_ON_OFF,0);
    }

    @Override
    public boolean getLedOn() {
        return false;
    }

    @Override
    public boolean setGoalPosition(int position) {
        try {
            sendWriteCommand(Register.GOAL_POSITION,position);
            return true;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        } catch (ErrorResponseException e) {
            log.severe(e.getMessage());
            return false;
        }
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

//    Data sendReadCommand(Register register) throws ResponseParsingException {
//        byte[] packet = new PacketV1().buildReadData(servoId, register.getAddress(), register.getSize());
//        byte[] response = controller.getPort().sendAndReceive(packet);
//        Data data = null;
//        try {
//            data = new PacketV1().parseFirst(response);
//        } catch (ErrorResponseException e) {
//            log.severe(e.toString());
//            return data;
//        }
//        return data;
//    }


    public void sendWriteCommand(Register register, int value) throws ResponseParsingException,ErrorResponseException {
        byte[] packet=null;
        if (register.getSize() == 2) {
            packet = new PacketV1().buildWriteData(servoId, register.getAddress(), value%256, value/256);
        } else if (register.getSize() == 1) {
            packet = new PacketV1().buildWriteData(servoId, register.getAddress(), value);
        }
        byte[] response = controller.getPort().sendAndReceive(packet);
        new PacketV1().parseFirst(response);

    }

    private int sendReadCommand(Register register) throws ResponseParsingException {
        byte[] packet = new PacketV1().buildReadData(servoId, register.getAddress(), register.getSize());
        byte[] response = controller.getPort().sendAndReceive(packet);
        Data data = null;
        try {
            data = new PacketV1().parseFirst(response);
        } catch (ErrorResponseException e) {
            log.severe(e.toString());
            return -1;
        }
        return data.result;
    }

    private boolean sendWriteCommandNoEx(Register r, int v) {
        try {
            sendWriteCommand(r,v);
            return true;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        } catch (ErrorResponseException e) {
            log.severe(e.getMessage());
            return false;
        }
    }

}
