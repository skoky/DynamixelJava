package cz.skoky.xl320;

import jssc.SerialPortException;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by skokan on 30.4.15.
 */
public class SerialReadTest {

    @Test
    public void testSerialReading() throws SerialPortException {
        byte[] buf = new byte[]{3,1,0x36,0,0};
        USBPort port = new USBPort("/dev/ttyACM0");
        port.openPort();
        port.writeData(buf);
        Integer response = port.readResponse();
        System.out.println("Response:"+ response);
        port.closePort();
    }
}
