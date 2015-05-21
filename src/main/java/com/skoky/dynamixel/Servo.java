package com.skoky.dynamixel;

import com.skoky.dynamixel.servo.ReturnLevel;
import com.skoky.dynamixel.servo.LedColor;

/**
 * Created by skoky on 5.5.15.
 */
public interface Servo {

    int getModelNumber();

    int getFirmwareVersion();

    int getId();
    boolean setId(int newId);

    int getBaudRate();
    boolean setBaudrate(int speed);

    boolean setReturnDelayTime(int time);
    int getReturnDelayTime();

    boolean setCWAngleLimit(int limit);
    int getCWAngleLimit();

    boolean setCCWAngleLimit(int limit);
    int getCCWAngleLimit();

    boolean setTemperatureLimit(int limit);
    int getTemperatureLimit();

    boolean setLowestLimitVoltage(float limit);
    float getLowestLimitVoltage();

    boolean setHighestLimitVoltage(float limit);
    float getHighestLimitVoltage();

    boolean setMaxTorque(int torqueLimit);
    int getMaxTorque();

    void setReturnLevel(ReturnLevel level);
    ReturnLevel getReturnLevel();

    void setAlarmLed(boolean onOff);
    int getAlarmLed();

    boolean setAlarmShutdown(int value);
    int getAlarmShutdown();

    boolean setTorqueEnable(boolean enable);
    boolean getTorqueEnabled();

    boolean setLedOn(LedColor on);
    LedColor getLedOn();

    // TODO compliance margins

    boolean setGoalPosition(int position);
    int getGoalPosition();

    boolean setMovingSpeed(int speed);
    int getMovingSpeed();

    boolean setTorqueLimit(int limit);
    int getTorqueLimit();

    int getPresentPosition();
    int getPresentSpeed();
    int getPresentLoad();
    float getPresentVoltage();
    int getPresentTemperature();
    int getRegistered();
    Boolean isMoving();

    void setEEPROMLock(boolean locked);
    boolean getEEPROMLock();

    void setPunch(int punch);
    int getPunch();

    boolean isWheelMode();
    boolean isJointMode();
    boolean setWheelMode();
    boolean setJointMode();

    int getGoalVelocity();
    boolean setGoalVelocity(int velocity);

    int getHWStatusError();

    boolean setGoalPositionAndWait(int pos);
}
