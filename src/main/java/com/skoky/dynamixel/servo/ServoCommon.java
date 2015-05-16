package com.skoky.dynamixel.servo;

import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.err.ErrorResponseException;
import com.skoky.dynamixel.err.ResponseParsingException;
import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.raw.Data;
import com.skoky.dynamixel.raw.PacketCommon;
import com.skoky.dynamixel.servo.xl320.Register;

import java.util.logging.Logger;

/**
 * Created by skokan on 14.5.15.
 */
public abstract class ServoCommon {

    protected Logger log = Logger.getGlobal();

}
