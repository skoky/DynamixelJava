package com.skoky.dynamixel.servo;

/**
 * Created by skokan on 14.5.15.
 */
public enum ReturnLevel {
    NO_RETURN(0),
    READ_ONLY(1),
    RETURN_ALL(2),
    RETUR_LEVEL_UNKNOWN(-1);

    private final int id;

    ReturnLevel(int id) {
        this.id=id;
    }

    public static ReturnLevel getById(int levelId) {
        for(ReturnLevel r: ReturnLevel.values()) {
            if (r.id==levelId) return r;
        }
        return RETUR_LEVEL_UNKNOWN;
    }
}
