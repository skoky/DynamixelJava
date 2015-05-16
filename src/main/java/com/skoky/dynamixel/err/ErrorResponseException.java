package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ErrorResponseException extends Throwable {

    private final int errorCode;
    private final String errorName;

    public ErrorResponseException(int errorCode) {
        super("Error code:"+errorCode);
        this.errorCode=errorCode;
        this.errorName=getErrorName();
    }


    public String getErrorName() {
        switch (errorCode) {
            case 64:
                return "Instruction Error";
            case 32:
                return "Overload Error";
            case 16:
                return "CRC Error";
            case 8:
                return "Range error";
            case 4:
                return "Overheat error";
            case 2:
                return "Angle Limit Error";
            default:return "Unknown error code: "+errorCode;
        }
    }

    @Override
    public String toString() {
        return "ErrorResponseException{" +
                "errorName=" + errorName+
                '}';
    }
}
