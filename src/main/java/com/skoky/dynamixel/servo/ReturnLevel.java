package com.skoky.dynamixel.servo;

/**
 * Created by skokan on 14.5.15.
 */
public enum ReturnLevel {
    NO_RETURN(0),
    READ_ONLY(1),
    RETURN_ALL(2);

    private final int id;

    ReturnLevel(int id) {
        this.id=id;
    }
}
