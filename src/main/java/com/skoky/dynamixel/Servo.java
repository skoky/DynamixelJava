package com.skoky.dynamixel;

/**
 * Created by skoky on 5.5.15.
 */
public interface Servo {

    public int getPresentPosition();

    int getModelNumber();

    int getFirmwareVersion();


    int getCWAngleLimit();

    void setCWLimit(int i);
}
