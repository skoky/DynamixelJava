package com.skoky.dynamixel.servo.xl320;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ErrorResponseV2Exception;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.port.SerialPortException;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.Instruction;
import com.skoky.dynamixel.raw.Packet;
import com.skoky.dynamixel.raw.PacketV2;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.ReturnLevel;
import com.skoky.dynamixel.servo.ServoCommon;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoXL320 extends ServoCommon implements Servo {
    private static Logger log = Logger.getGlobal();
    private final Controller controller;

    public ServoXL320(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller= controller;
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
            int r = sendReadCommand(Register.MOVING);
            if (r==0) return false;
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
        try {
            int m = sendReadCommand(Register.CONTROL_MODE);
            if (m==1) return true;
            else return false;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isJointMode() {
        try {
            int m = sendReadCommand(Register.CONTROL_MODE);
            if (m==2) return true;
            else return false;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean setWheelMode() {
        boolean done = setTorqueEnable(false);
        if (!done) return false;
        done = sendWriteCommandNoEx(Register.CONTROL_MODE, 1);
        if (!done) return false;
        done = setTorqueEnable(true);
        if (!done) return false;
        return true;
    }

    @Override
    public boolean setJointMode() {
        boolean done = setTorqueEnable(false);
        if (!done) return false;
        done = sendWriteCommandNoEx(Register.CONTROL_MODE, 2);
        if (!done) return false;
        done = setTorqueEnable(true);
        if (!done) return false;
        return true;
    }

    @Override
    public int getGoalVelocity() {
        try {
            return sendReadCommand(Register.GOAL_VELOCITY);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setGoalVelocity(int velocity) {
        return sendWriteCommandNoEx(Register.GOAL_VELOCITY,velocity);
    }

    @Override
    public int getHWStatusError() {
        try {
            return sendReadCommand(Register.HW_ERROR_STATUS);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
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
            return sendReadCommand(Register.FIRMWARE_VERSION);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }


    @Override
    public int getId() {
        try {
            servoId = sendReadCommand(Register.ID);
            return servoId;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setId(int newId) {

        return false;
    }

    @Override
    public int getBaudRate() {
        try {
            return sendReadCommand(Register.BAUD_RATE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setBaudrate(int speed) {
        return sendWriteCommandNoEx(Register.BAUD_RATE,speed);
    }

    @Override
    public boolean setReturnDelayTime(int time) {

        return false;
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

        return false;
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
        return false;
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

        return false;
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
        try {
            int level = sendReadCommand(Register.RETURN_LEVEL);
            return ReturnLevel.getById(level);
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
        return -1;
    }

    @Override
    public boolean setAlarmShutdown(int value) {
        return sendWriteCommandNoEx(Register.SHUTDOWN_ALARM,value);
    }

    @Override
    public int getAlarmShutdown() {
        try {
            return sendReadCommand(Register.SHUTDOWN_ALARM);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setTorqueEnable(boolean enable) {
        int i = 0;
        if (enable) i=1;
        return sendWriteCommandNoEx(Register.TORQUE_ENABLE,i);
    }

    @Override
    public boolean getTorqueEnabled() {
        return false;
    }

    @Override
    public boolean setLedOn(LedColor color) {
        return sendWriteCommandNoEx(Register.LED_ON_OFF, color.getId());
    }

    @Override
    public LedColor getLedOn() {
        try {
            int colorId = sendReadCommand(Register.LED_ON_OFF);
            return LedColor.getById(colorId);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return LedColor.UNKNOWN;
        }

    }

    @Override
    public boolean setGoalPosition(int position) {
        return sendWriteCommandNoEx(Register.GOAL_POSITION,position);
    }


    @Override
    public int getGoalPosition() {
        try {
            return sendReadCommand(Register.GOAL_POSITION);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setMovingSpeed(int speed) {
        return false;   // not supported on this servo
    }

    @Override
    public int getMovingSpeed() {
        try {
            return sendReadCommand(Register.PRESENT_SPEED);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean setTorqueLimit(int limit) {
        return sendWriteCommandNoEx(Register.GOAL_TOURGE,limit);
    }

    @Override
    public int getTorqueLimit() {
        try {
            return sendReadCommand(Register.GOAL_TOURGE);
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return -1;
        }
    }



    int sendReadCommand(Register r) throws ResponseParsingException, SerialPortException {
        Packet p = new PacketV2();
        int rLow = r.getAddress();
        int rHigh = r.getAddress() / 256;
        byte[] posCommand = p.buildPacket(Instruction.READ, servoId, rLow, rHigh, r.getSize(), 0);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<Data> d = p.parse(response);
        if (d.size()!=1)
            throw new ResponseParsingException("Invalid response");

        if (d.get(0).params==null || d.get(0).params.length!=r.getSize())
            throw new ResponseParsingException("Invalid response, params");

        Data data = d.get(0);
        if (r.getSize()>=1)
            data.result = data.params[0];
        if (r.getSize()==2)
            data.result += data.params[1]*256;
        return data.result;
    }

    private boolean sendWriteCommandNoEx(Register r, int v) {
        try {
            sendWriteCommand(r, v);
            return true;
        } catch (ResponseParsingException e) {
            log.severe(e.getMessage());
            return false;
        } catch (ErrorResponseV2Exception e) {
            log.severe(e.getErrorName());
            return false;
        }
    }

    public Data sendWriteCommand(Register r, int limit) throws ResponseParsingException, ErrorResponseV2Exception {
        if (limit < r.getMin() || limit > r.getMax()) throw new IllegalArgumentException("Value over limits");
        if (r.isReadOnly()) throw new IllegalArgumentException("Register is read-only!");

        int rLow = r.getAddress();
        int rHigh = r.getAddress() / 256;
        Packet p = new PacketV2();
        byte[] posCommand = new byte[0];
        if (r.getSize() == 1)
            posCommand = p.buildPacket(Instruction.WRITE,servoId, rLow, rHigh, limit);
        else if (r.getSize() == 2)
            posCommand = p.buildPacket(Instruction.WRITE, servoId, rLow, rHigh, limit % 256, limit / 256);
        byte[] response = controller.getPort().sendAndReceive(posCommand);
        List<Data> d = p.parse(response);
        Data first = d.get(0);
        if (first.error!=0)
            throw new ErrorResponseV2Exception(first.error);
        else
            return first;
    }

    @Override
    public String toString() {
        return "ServoXL320{" +
                "servoId=" + servoId +
                ", controller=" + controller.getPort() +
                '}';
    }
}
