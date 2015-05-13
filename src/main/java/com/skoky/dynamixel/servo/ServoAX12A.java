package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;

/**
 * Created by skoky on 5.5.15.
 */
public class ServoAX12A implements Servo {
    private final int servoId;
    private final Controller controller;

    public ServoAX12A(int servoId, Controller controller) {
        this.servoId=servoId;
        this.controller=controller;
    }

    @Override
    public int getPresentPosition() {
        return 0;
    }

    @Override
    public int getModelNumber() {
        return 0;
    }

    @Override
    public int getFirmwareVersion() {
        return 0;
    }

    @Override
    public int getCWAngleLimit() {
        return 0;
    }

    @Override
    public void setCWLimit(int i) {

    }

    @Override
    public String toString() {
        return "ServoAX12A{" +
                "servoId=" + servoId +
                ", controller=" + controller +
                '}';
    }
}
