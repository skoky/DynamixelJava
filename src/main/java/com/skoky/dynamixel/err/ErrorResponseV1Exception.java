package com.skoky.dynamixel.err;

/**
 * Created by skokan on 14.5.15.
 */
public class ErrorResponseV1Exception extends Throwable {

    private final int errorCode;
    private final String errorName;

    String[] bitsError = new String[] {"",
        "0:Input Voltage Error",
        "1:Angle Limit Error",
        "2:Overheating Error",
        "3:Range Error",
        "4:Checksum Error",
        "5:Overload Error",
        "6:Instruction Error",
        "7:-"};

    public ErrorResponseV1Exception(int errorCode) {
        super("Error code:"+errorCode);
        this.errorCode=errorCode;
        this.errorName=getErrors();
    }
    private String getErrors() {
        StringBuilder sb = new StringBuilder();
        String errorBits = Integer.toBinaryString(errorCode);
        int epos=0;
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
