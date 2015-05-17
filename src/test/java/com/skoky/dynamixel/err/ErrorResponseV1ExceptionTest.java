package com.skoky.dynamixel.err;

import com.skoky.dynamixel.err.ErrorResponseV1Exception;
import com.skoky.dynamixel.err.ErrorResponseV2Exception;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by skoky on 17.5.15.
 */
public class ErrorResponseV1ExceptionTest {

    @Test
    public void toStringTest() {
        ErrorResponseV1Exception e = new ErrorResponseV1Exception(0);
        assertEquals("",e.getErrorName());

        e = new ErrorResponseV1Exception(1);
        assertEquals("0:Input Voltage Error",e.getErrorName());

        e = new ErrorResponseV1Exception(2);
        assertEquals("1:Angle Limit Error",e.getErrorName());

        e = new ErrorResponseV1Exception(3);
        assertEquals("0:Input Voltage Error,1:Angle Limit Error",e.getErrorName());

        e = new ErrorResponseV1Exception(135);
        assertEquals("0:Input Voltage Error,1:Angle Limit Error,2:Overheating Error,7:-",e.getErrorName());

    }
}
