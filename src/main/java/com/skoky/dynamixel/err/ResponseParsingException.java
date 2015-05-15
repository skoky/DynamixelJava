package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ResponseParsingException extends Throwable {

    private final String msg;

    public ResponseParsingException(String s) {
        this.msg=s;
    }

    @Override
    public String toString() {
        return "ResponseParsingException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
