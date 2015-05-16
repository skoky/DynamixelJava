package com.skoky.dynamixel;

import com.skoky.dynamixel.servo.Baudrate;
import com.skoky.dynamixel.servo.ReturnLevel;

/**
 * Created by skoky on 5.5.15.
 */
public interface Servo {

    int getModelNumber();

    int getFirmwareVersion();

    int getId();
    void setId(int newId);

    Baudrate getBaudRate();
    void setBaudrate(Baudrate b);

    void setReturnDelayTime(int time);
    int getReturnDelayTime();

    void setCWAngleLimit(int limit);
    int getCWAngleLimit();

    void setCCWAngleLimit(int limit);
    int getCCWAngleLimit();

    void setTemperatureLimit(int limit);
    int getTemperatureLimit();

    void setLowestLimitVoltage(int limit);
    int getLowestLimitVoltage();

    void setHighestLimitVoltage(int limit);
    int getHifgestLimitVoltage();

    boolean setMaxTorque(int torqueLimit);
    int getMaxTorque();

    void setReturnLevel(ReturnLevel level);
    ReturnLevel getReturnLevel();

    void setAlarmLed(boolean onOff);
    boolean getAlarmLed();

    void setAlarmShutdown(int value);
    int getAlarmShutdown();

    void setTorqueEnable(boolean enable);
    boolean getTorqueEnabled();

    boolean setLedOn(boolean on);
    boolean getLedOn();

    // TODO compliance margins

    void setGoalPosition(int position);
    int getGoalPosition();

    void setMovingSpeed(int speed);
    int getMovingSpeed();

    void setTorqueLimit(int limit);
    int getTorqueLimit();

    public int getPresentPosition();
    int getPresentSpeed();
    int getPresentLoad();
    int getPresentVoltage();
    int getPresentTemperature();
    int getRegistered();
    int isMoving();

    void setEEPROMLock(boolean locked);
    boolean getEEPROMLock();

    void setPunch(int punch);
    int getPunch();

}
