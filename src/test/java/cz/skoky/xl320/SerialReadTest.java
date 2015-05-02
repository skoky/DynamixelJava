package cz.skoky.xl320;

import jssc.SerialPortException;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by skokan on 30.4.15.
 */
public class SerialReadTest {

    @Test
    public void testSerialReading() throws SerialPortException, InterruptedException {
        byte[] buf = new byte[]{4,1,2,0x54,0};
        USBPort port = new USBPort("/dev/ttyACM0");
        port.openPort();
        // port.writeData(buf);
        while(true) {
            Thread.sleep(3000);
            port.writeData(buf);
            Integer response = port.readResponse(7);
            System.out.println("Response:" + response);

        }
        // port.closePort();
    }
}
