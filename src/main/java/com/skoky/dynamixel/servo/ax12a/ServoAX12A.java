package com.skoky.dynamixel.servo.ax12a;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ErrorResponseV1Exception;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.PacketV1;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.ReturnLevel;

import java.util.logging.Logger;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoAX12A implements Servo {
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
        try {
            return sendReadCommand(Register.PRESENT_LOAD);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public float getPresentVoltage() {
        try {
            return sendReadCommand(Register.PRESENT_VOLTAGE)/10;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getPresentTemperature() {
        try {
            return sendReadCommand(Register.PRESENT_TEMPERATURE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getRegistered() {
        return 0;
    }

    @Override
    public Boolean isMoving() {
        try {
            int moving = sendReadCommand(Register.MOVING);
            if (moving==0) return false;
            else return true;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return null;
        }

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
    public boolean isWheelMode() {
        int cw = getCWAngleLimit();
        int ccw = getCCWAngleLimit();
        if (cw == 0 && ccw == 0 ) return true;
        else return false;
    }

    @Override
    public boolean isJointMode() {
        int cw = getCWAngleLimit();
        int ccw = getCCWAngleLimit();
        if (cw == 0 && ccw == 0 ) return false;
        else return true;

    }

    @Override
    public boolean setWheelMode() {
        setCCWAngleLimit(0);
        setCCWAngleLimit(0);
        return false;
    }

    @Override
    public boolean setJointMode() {
        setCCWAngleLimit(1023);
        setCCWAngleLimit(1023);
        return false;
    }

    @Override
    public int getGoalVelocity() {
        return getMovingSpeed();
    }

    @Override
    public boolean setGoalVelocity(int velocity) {
        return setMovingSpeed(velocity);
    }

    @Override
    public int getHWStatusError() {
        throw new IllegalStateException("Not supported");
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
        try {
            return sendReadCommand(Register.ID);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setId(int newId) {
        return sendWriteCommandNoEx(Register.ID,newId);
    }

    @Override
    public int getBaudRate() {
        int value = 0;
        try {
            return sendReadCommand(Register.BAUD_RATE);
        } catch (ResponseParsingException e) {
            log.severe("Unable to parse baudrate response");
            return -1;
        }
    }

    @Override
    public boolean setBaudrate(int speed) {
        return sendWriteCommandNoEx(Register.BAUD_RATE,speed);
    }

    @Override
    public boolean setReturnDelayTime(int time) {
        return sendWriteCommandNoEx(Register.DELAY_TIME,time);
    }

    @Override
    public int getReturnDelayTime() {
        try {
            return sendReadCommand(Register.DELAY_TIME);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setCWAngleLimit(int limit) {
        return sendWriteCommandNoEx(Register.CW_ANGLE_LIMIT,limit);
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
    public boolean setCCWAngleLimit(int limit) {
        return sendWriteCommandNoEx(Register.CCW_ANGLE_LIMIT,limit);
    }

    @Override
    public int getCCWAngleLimit() {
        try {
            return sendReadCommand(Register.CCW_ANGLE_LIMIT);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;

        }
    }

    @Override
    public boolean setTemperatureLimit(int limit) {
        return sendWriteCommandNoEx(Register.LIMIT_TEMPERATURE,limit);
    }

    @Override
    public int getTemperatureLimit() {
        try {
            return sendReadCommand(Register.LIMIT_TEMPERATURE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setLowestLimitVoltage(float limit) {
        return sendWriteCommandNoEx(Register.LOWER_LIMIT_VOLTAGE, (int) (limit*10));
    }

    @Override
    public float getLowestLimitVoltage() {
        try {
            return sendReadCommand(Register.LOWER_LIMIT_VOLTAGE)/10;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setHighestLimitVoltage(float limit) {
        return sendWriteCommandNoEx(Register.UPPER_LIMIT_VOLTAGE, (int) (limit*10));
    }

    @Override
    public float getHighestLimitVoltage() {
        try {
            return sendReadCommand(Register.UPPER_LIMIT_VOLTAGE)/10;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
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
        int levelId = 0;
        try {
            levelId = sendReadCommand(Register.STATUS_RETURN_LEVEL);
            return ReturnLevel.getById(levelId);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return ReturnLevel.RETUR_LEVEL_UNKNOWN;
        }

    }

    @Override
    public void setAlarmLed(boolean onOff) {

    }

    @Override
    public int getAlarmLed() {
        try {
            return sendReadCommand(Register.ALARM_LED);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setAlarmShutdown(int value) {
        return sendWriteCommandNoEx(Register.SHUTDOWN_ALARM,value);
    }

    @Override
    public int getAlarmShutdown() {
        return 0;
    }

    @Override
    public boolean setTorqueEnable(boolean enable) {

        return enable;
    }

    @Override
    public boolean getTorqueEnabled() {
        return false;
    }

    @Override
    public boolean setLedOn(LedColor on) {
        if (on==LedColor.OFF)
            return sendWriteCommandNoEx(Register.LED_ON_OFF,0);
        else
            return sendWriteCommandNoEx(Register.LED_ON_OFF,1);
    }

    @Override
    public LedColor getLedOn() {
        int colorId = 0;
        try {
            colorId = sendReadCommand(Register.LED_ON_OFF);
            if (colorId==0) return LedColor.OFF;
            else return LedColor.WHITE;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return LedColor.UNKNOWN;
        }
    }

    @Override
    public boolean setGoalPosition(int position) {
        try {
            sendWriteCommand(Register.GOAL_POSITION,position);
            return true;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        } catch (ErrorResponseV1Exception e) {
            log.severe(e.getErrorName());
            return false;
        }
    }

    @Override
    public int getGoalPosition() {
        return 0;
    }

    @Override
    public boolean setMovingSpeed(int speed) {
        return sendWriteCommandNoEx(Register.MOVING_SPEED,speed);
    }

    @Override
    public int getMovingSpeed() {
        try {
            return sendReadCommand(Register.MOVING_SPEED);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setTorqueLimit(int limit) {
        return sendWriteCommandNoEx(Register.GOAL_TORQUE, limit);

    }

    @Override
    public int getTorqueLimit() {
        try {
            return sendReadCommand(Register.GOAL_TORQUE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

//    Data sendReadCommand(Register register) throws ResponseParsingException {
//        byte[] packet = new PacketV1().buildReadData(servoId, register.getAddress(), register.getSize());
//        byte[] response = controller.getPort().sendAndReceive(packet);
//        Data data = null;
//        try {
//            data = new PacketV1().parseFirst(response);
//        } catch (ErrorResponseV1Exception e) {
//            log.severe(e.toString());
//            return data;
//        }
//        return data;
//    }


    public void sendWriteCommand(Register register, int value) throws ResponseParsingException,ErrorResponseV1Exception {
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
        } catch (ErrorResponseV1Exception e) {
            log.severe(e.getErrorName());
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
        } catch (ErrorResponseV1Exception e) {
            log.severe(e.getErrorName());
            return false;
        }
    }

}
