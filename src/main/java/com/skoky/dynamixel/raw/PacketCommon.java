package com.skoky.dynamixel.raw;

import java.util.Arrays;

/**
 * Created by skoky on 9.5.15.
 */
public class PacketCommon {


    protected byte[] toByteArray(int[] buffer) {
        byte[] x = new byte[buffer.length];
        for(int i=0; i<buffer.length;i++) {
            x[i]= (byte) buffer[i];
        }
        return x;
    }


    public class Data {
        public final TYPES type;
        public int servoId;
        public int[] params;
        public int error;

        public Data(TYPES type) {
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



    public static enum TYPES{
        PING(1),
        READ(2),
        WRITE(3),
        ACTION(5),
        FACTORY_RESET(6),
        REBOOT(8),
        STATUS(0x55),
        SYNC_READ(0x82),
        SYNC_WRITE(0x83),
        BULK_READ(0x92),
        BULK_WRITE(0x93),
        NONE_V1(0),
        UNKNOWN(0xFF);


        private final int typeId;

        TYPES(int typeId) {
            this.typeId=typeId;
        }

        public static TYPES getByNumber(byte id) {
            for(TYPES t : values()) {
                if (t.typeId==id) return t;
            }
            return UNKNOWN;
        }
    }
}
