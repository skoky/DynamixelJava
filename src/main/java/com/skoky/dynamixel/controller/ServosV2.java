package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.ReturnLevel;
import com.skoky.dynamixel.servo.xl320.Register;
import org.apache.commons.codec.binary.Hex;

import java.util.List;

/**
 * Created by skoky on 22.5.15.
 */

public class ServosV2 implements Servo {
    private List<Servo> servos;
    private SerialPort port;

    @Override
    public int getModelNumber() {
        byte[] request = new PacketV2().buildBulkReadData(servos, 1, Register.MODEL_NUMBER.getAddress(), 0, Register.MODEL_NUMBER.getSize(), 0,
                                                                  2, Register.MODEL_NUMBER.getAddress(), 0, Register.MODEL_NUMBER.getSize(), 0,
                                                                  3, Register.MODEL_NUMBER.getAddress(), 0, Register.MODEL_NUMBER.getSize(), 0);
        byte[] response = port.sendAndReceive(request,500);
        System.out.println("Model number:"+Hex.encodeHexString(response));
        return 0;
    }

    @Override
    public int getFirmwareVersion() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean setId(int newId) {
        return false;
    }

    @Override
    public int getBaudRate() {
        return 0;
    }

    @Override
    public boolean setBaudrate(int speed) {
        return false;
    }

    @Override
    public boolean setReturnDelayTime(int time) {
        return false;
    }

    @Override
    public int getReturnDelayTime() {
        return 0;
    }

    @Override
    public boolean setCWAngleLimit(int limit) {
        return false;
    }

    @Override
    public int getCWAngleLimit() {
        return 0;
    }

    @Override
    public boolean setCCWAngleLimit(int limit) {
        return false;
    }

    @Override
    public int getCCWAngleLimit() {
        return 0;
    }

    @Override
    public boolean setTemperatureLimit(int limit) {
        return false;
    }

    @Override
    public int getTemperatureLimit() {
        return 0;
    }

    @Override
    public boolean setLowestLimitVoltage(float limit) {
        return false;
    }

    @Override
    public float getLowestLimitVoltage() {
        return 0;
    }

    @Override
    public boolean setHighestLimitVoltage(float limit) {
        return false;
    }

    @Override
    public float getHighestLimitVoltage() {
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
    public int getAlarmLed() {
        return 0;
    }

    @Override
    public boolean setAlarmShutdown(int value) {
        return false;
    }

    @Override
    public int getAlarmShutdown() {
        return 0;
    }

    @Override
    public boolean setTorqueEnable(boolean enable) {
        return false;
    }

    @Override
    public boolean getTorqueEnabled() {
        return false;
    }

    @Override
    public boolean setLedOn(LedColor color) {
        return buildBulkWrite(servos, Register.LED_ON_OFF,color);
    }

    private boolean buildBulkWrite(List<Servo> servos, Register r, LedColor v) {
        byte[] request = new PacketV2().buildBulkWriteData(servos, 1, 25,0,1,0,0,
                                                                   2, 25,0,2,0,0,
                                                                    3, 25,0,2,0,0);
        byte[] response = port.sendAndReceive(request,200);
        System.out.println("Bulk response:"+ Hex.encodeHexString(response));
        return true;

    }

    @Override
    public LedColor getLedOn() {
        byte[] request = new PacketV2().buildBulkReadData(servos,1, 25,0, 1,0);
        byte[] response = port.sendAndReceive(request);
        System.out.println("Bulk response:"+ Hex.encodeHexString(response));
        return null;
    }

    @Override
    public boolean setGoalPosition(int position) {
        return false;
    }

    @Override
    public int getGoalPosition() {
        return 0;
    }

    @Override
    public boolean setMovingSpeed(int speed) {
        return false;
    }

    @Override
    public int getMovingSpeed() {
        return 0;
    }

    @Override
    public boolean setTorqueLimit(int limit) {
        return false;
    }

    @Override
    public int getTorqueLimit() {
        return 0;
    }

    @Override
    public int getPresentPosition() {
        return 0;
    }

    @Override
    public int getPresentSpeed() {
        return 0;
    }

    @Override
    public int getPresentLoad() {
        return 0;
    }

    @Override
    public float getPresentVoltage() {
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
    public Boolean isMoving() {
        return null;
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
        return false;
    }

    @Override
    public boolean isJointMode() {
        return false;
    }

    @Override
    public boolean setWheelMode() {
        return false;
    }

    @Override
    public boolean setJointMode() {
        return false;
    }

    @Override
    public int getGoalVelocity() {
        return 0;
    }

    @Override
    public boolean setGoalVelocity(int velocity) {
        return false;
    }

    @Override
    public int getHWStatusError() {
        return 0;
    }

    @Override
    public boolean setGoalPositionAndWait(int pos) {
        return false;
    }

    public void setServos(List<Servo> servos, SerialPort port) {
        this.servos=servos;
        this.port = port;
    }
}