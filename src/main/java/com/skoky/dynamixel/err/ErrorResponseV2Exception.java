package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ErrorResponseV2Exception extends Throwable {

    private final int errorCode;
    private final String errorName;


    String[] bitsError = new String[] {"",
            "1:Result failed",
            "2:Instruction error",
            "4:CRC error",
            "8:Data range error",
            "16:Data length error",
            "32:Data limit error",
            "64:Access error",
            "128:Warning: probably low voltage"};

    public ErrorResponseV2Exception(int errorCode) {
        super("Error code:"+errorCode);
        this.errorCode=errorCode;
        this.errorName=getErrors();
    }

    private String getErrors() {
        StringBuilder sb = new StringBuilder();
        String errorBits = Integer.toBinaryString(errorCode);
        int epos;
        for(int i=errorBits.length()-1; i>=0;i--) {
            if (errorBits.charAt(i)=='1') {
                epos = errorBits.length() - i;
                sb.append(bitsError[epos]);
                sb.append(",");
            }
        }
        if (sb.length()==0) return "";
        return sb.substring(0,sb.length()-1);
    }

    @Override
    public String toString() {
        return "ErrorResponseV1Exception{" +
                "errors=" + errorName+
                '}';
    }

    public String getErrorName() {
        return errorName;
    }
}
