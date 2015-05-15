package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class SerialLinkError extends RuntimeException {
    private final String msg;

    public SerialLinkError(String s) {
        this.msg=s;
    }

    @Override
    public String toString() {
        return "SerialLinkError{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
