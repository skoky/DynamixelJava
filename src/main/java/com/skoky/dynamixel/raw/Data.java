package com.skoky.dynamixel.raw;

import java.util.Arrays;

/**
 * Created by skoky on 16.5.15.
 */


public class Data {
    public final PacketCommon.TYPES type;
    public int servoId;
    public int[] params;
    public int error;
    public int result;

    public Data(PacketCommon.TYPES type) {
        this.type=type;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type=" + type +
                ", servoId=" + servoId +
                ", error="+error +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
