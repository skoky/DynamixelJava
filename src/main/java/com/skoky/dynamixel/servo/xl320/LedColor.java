package com.skoky.dynamixel.servo.xl320;

/**
 * Created by skokan on 29.4.15.
 */
public enum LedColor {
    OFF(0),
    WHITE(7),
    PINK(5),
    BLUE_GREEN(6),
    YELLOW(3),
    BLUE(4),
    GREEN(2),
    RED(1),
    UNKNOWN(-1);


    private int id;

    LedColor(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public static LedColor getById(int colorId) {
        for(LedColor c : LedColor.values())
            if (c.id==colorId) return c;
        return UNKNOWN;
    }
}
