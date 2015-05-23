package com.skoky.dynamixel.servo;

import java.util.logging.Logger;

/**
 * Created by skoky on 17.5.15.
 */
public abstract class ServoCommon {

    protected int servoId;

    private Logger log = Logger.getGlobal();

    public boolean setGoalPositionAndWait(int pos) {
        boolean done = setGoalPosition(pos);
        if (!done) return done;
        for(int i=0;i<50;i++) {     // loop for 5secs
            if (!isMoving()) return true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.warning(e.getMessage());
            }
        }
        return false;  // timeout waiting for servo
    }

    protected abstract Boolean isMoving();

    protected abstract boolean setGoalPosition(int pos);

    public int getServoId() {
        return servoId;
    }
}
