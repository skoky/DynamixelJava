package com.skoky.dynamixel.raw;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    byte[] buildPing();
    byte[] buildWriteDate(int servoId, int... params);

    void parse(byte[] p);
}
