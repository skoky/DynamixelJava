package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.err.ErrorResponseV1Exception;
import com.skoky.dynamixel.err.ResponseParsingException;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    static final int BROADCAST = 0xFE;

    byte[] buildMultiPacket(Instruction instr, int... params);
    byte[] buildPacket(Instruction instr, int servoId, int... params);

    List<Data> parse(byte[] p) throws ResponseParsingException;
    Data parseFirst(byte[] p) throws ResponseParsingException, ErrorResponseV1Exception;

}
