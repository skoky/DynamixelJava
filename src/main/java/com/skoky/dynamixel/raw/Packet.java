package com.skoky.dynamixel.raw;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    byte[] buildPing();
    byte[] buildWriteDate(int servoId, int... params);

    List<PacketV2.Data> parse(byte[] p);
}
