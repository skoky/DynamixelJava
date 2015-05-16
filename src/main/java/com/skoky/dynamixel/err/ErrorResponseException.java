package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ErrorResponseException extends Throwable {

    public ErrorResponseException(int errorCode, String s) {
        super(s + ": " +errorCode);
    }

}
