package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ErrorResponseV1Exception;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.servo.LedColor;
import com.skoky.dynamixel.servo.xl320.Register;

import java.util.List;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    byte[] buildPing();
    byte[] buildPing(int servoId);
    byte[] buildWriteData(int servoId, int... params);
    byte[] buildReadData(int servoId, int... params);
    byte[] buildReset();

    List<Data> parse(byte[] p) throws ResponseParsingException;
    Data parseFirst(byte[] p) throws ResponseParsingException, ErrorResponseV1Exception;


    byte[] buildReboot();

    byte[] buildBulkWriteData(List<Servo> servos, int... params);
}
