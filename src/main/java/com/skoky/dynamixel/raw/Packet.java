package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.err.ResponseParsingException;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    byte[] buildPing();
    byte[] buildPing(int servoId);
    byte[] buildWriteData(int servoId, int... params);
    byte[] buildReadData(int servoId, int... params);

    List<PacketV2.Data> parse(byte[] p) throws ResponseParsingException;

}
