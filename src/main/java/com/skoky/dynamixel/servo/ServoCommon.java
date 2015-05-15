package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.servo.xl320.Register;

import java.util.logging.Logger;

/**
 * Created by skokan on 14.5.15.
 */
public abstract class ServoCommon implements Servo {

    protected Logger log = Logger.getGlobal();

    @Override
    public int getPresentSpeed() {
        two
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
            PacketCommon.Data data = sendReadCommand(Register.MODEL_NUMBER);
            return data.params[0] + data.params[1]*256;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getFirmwareVersion() {
        try {
            PacketCommon.Data data = sendReadCommand(Register.FIRMWARE_VERSION);
            return data.params[0];
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    abstract PacketCommon.Data sendReadCommand(Register register) throws ResponseParsingException;

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
            PacketCommon.Data data = sendReadCommand(Register.CW_ANGLE_LIMIT);
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
    public void setMaxTorque(int torqueLimit) {

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
    public void setLedOn(boolean on) {

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

    @Override
    public int getPresentPosition() {
        return 0;
    }

    protected abstract void setTwoByteRegister(Register cwAngleLimit, int limit) throws ResponseParsingException;

}
