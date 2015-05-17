package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ErrorResponseV2Exception extends Throwable {

    private final int errorCode;
    private final String errorName;

    public ErrorResponseV2Exception(int errorCode) {
        super("Error code:"+errorCode);
        this.errorCode=errorCode;
        this.errorName=getErrorName();
    }


    public String getErrorName() {
        switch (errorCode) {
            case 1:
                return "Result failed";
            case 2:
                return "Instruction error";
            case 4:
                return "CRC error";
            case 8:
                return "Data range error";
            case 16:
                return "Data length error";
            case 32:
                return "Data limit error";
            case 64:
                return "Access error";
            case 128:
                return "Warning: probably low voltage";
            default:return "Unknown error code: "+errorCode;
        }
    }

    @Override
    public String toString() {
        return "ErrorResponseV1Exception{" +
                "errorName=" + errorName+
                '}';
    }
}
