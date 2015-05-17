package com.skoky.dynamixel.err;

import com.skoky.dynamixel.err.ErrorResponseV2Exception;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by skoky on 17.5.15.
 */
public class ErrorResponseV2ExceptionTest {

    @Test
    public void toStringTest() {
        ErrorResponseV2Exception e = new ErrorResponseV2Exception(0);
        assertEquals("",e.getErrorName());

        e = new ErrorResponseV2Exception(1);
        assertEquals("1:Result failed",e.getErrorName());

        e = new ErrorResponseV2Exception(2);
        assertEquals("2:Instruction error",e.getErrorName());

        e = new ErrorResponseV2Exception(3);
        assertEquals("1:Result failed,2:Instruction error",e.getErrorName());

        e = new ErrorResponseV2Exception(135);
        assertEquals("1:Result failed,2:Instruction error,4:CRC error,128:Warning: probably low voltage",e.getErrorName());

    }
}
