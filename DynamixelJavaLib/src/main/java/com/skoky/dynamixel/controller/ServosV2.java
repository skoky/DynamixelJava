package com.skoky.dynamixel.controller;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.ServoGroup;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.Instruction;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.ReturnLevel;
import com.skoky.dynamixel.servo.xl320.Register;
import org.apache.commons.codec.binary.Hex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by skoky on 22.5.15.
 */

public class ServosV2 implements ServoGroup {
    private int[] servos;
    private SerialPort port;
    private int cachedId;
    private static Logger log = Logger.getGlobal();

    @Override
    public Map<Integer,Integer> getModelNumber() {
        return buildSyncRead(Register.MODEL_NUMBER);
    }

    private Map<Integer,Integer> buildSyncRead(Register r) {
        int[] data=new int[servos.length+4];
        data[0]=r.getAddress();
        data[2]=r.getSize();
        for(int i=0;i<servos.length;i++)
            data[4+i]=servos[i];
        byte[] request = new PacketV2().buildMultiPacket(Instruction.SYNC_READ, data);
        log.fine("Sync read request:"+Hex.encodeHexString(request));
        byte[] response = port.sendAndReceive(request,500);
        log.fine("Sync read response:" + Hex.encodeHexString(response));
        List<Data> result = new PacketV2().parse(response);
        HashMap<Integer,Integer> resultMap = new HashMap<>();
        for(Data d : result) {
            resultMap.put(d.servoId,d.result);
        }
        return resultMap;
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
        return buildSyncWrite(Register.CCW_ANGLE_LIMIT,limit);
    }

    @Override
    public Map<Integer, Integer> getCCWAngleLimit() {
        return buildSyncRead(Register.CCW_ANGLE_LIMIT);
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
        return buildSyncWrite(Register.MAX_TORQUE,torqueLimit);
    }

    @Override
    public Map<Integer, Integer> getMaxTorque() {
        return buildSyncRead(Register.MAX_TORQUE);
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
        return buildSyncWrite(Register.LED_ON_OFF,color.getId());
    }

    private boolean buildSyncWrite(Register r, int v) {
        int m = r.getSize()==1? 2 : 3;  // single or two bytes data
        int[] data = new int[4+servos.length*m];
        data[0]=r.getAddress();
        data[2]=r.getSize();
        for(int i=0;i<servos.length;i++) {
            data[4+i*m]=servos[i];
            data[4+i*m+1]=v%256;
            if (m==3) data[4+i*m+2]=v/256;
        }
        byte[] request = new PacketV2().buildMultiPacket(Instruction.SYNC_WRITE, data);
        log.fine("Sync write request " +r + ": " + Hex.encodeHexString(request));
        port.send(request);
        return true;

    }

    @Override
    public Map<Integer,LedColor> getLedOn() {
        Map<Integer, Integer> result = buildSyncRead(Register.LED_ON_OFF);
        Map<Integer,LedColor> resultMap = new HashMap<>();
        for(Integer r : result.keySet())
            resultMap.put(r,LedColor.getById(result.get(r)));
        return resultMap;
    }

    @Override
    public boolean setGoalPosition(int position) {
        return buildSyncWrite(Register.GOAL_POSITION,position);
    }

    @Override
    public int getGoalPosition() {
        return 0;
    }

    @Override
    public boolean setMovingSpeed(int speed) {
        return buildSyncWrite(Register.GOAL_VELOCITY,speed);
    }

    @Override
    public Map<Integer, Integer> getMovingSpeed() {
        return buildSyncRead(Register.PRESENT_SPEED);
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
    public Map<Integer, Integer> getPresentPosition() {
        return buildSyncRead(Register.CURRENT_POSITION);
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
    public Map<Integer, Integer> getGoalVelocity() {
        return buildSyncRead(Register.GOAL_VELOCITY);
    }

    @Override
    public boolean setGoalVelocity(int velocity) {
        return buildSyncWrite(Register.GOAL_VELOCITY,velocity);
    }

    @Override
    public int getHWStatusError() {
        return 0;
    }

    @Override
    public boolean setGoalPositionAndWait(int pos) {
        return false;
    }

    @Override
    public int getServoId() {
        return 0;
    }


    public void setServos(List<Servo> servosList, SerialPort port) {
        servos = new int[servosList.size()];
        for(int i=0;i<servos.length;i++)
            servos[i]=servosList.get(i).getServoId();
        this.port = port;
    }
}