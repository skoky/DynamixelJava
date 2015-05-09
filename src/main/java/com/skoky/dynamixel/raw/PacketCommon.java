package com.skoky.dynamixel.raw;

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
}
