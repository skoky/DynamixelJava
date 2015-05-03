package cz.skoky.xl320;

import jssc.SerialPortException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Testing communication with OpenCM. This only works with OpenCM connected to /dev/ttyACM0 and servo on ID 1
 * Created by skokan on 30.4.15.
 */
public class SerialReadTest {

    private USBPort port;

    @Before
    public void setUp() throws SerialPortException {
        port = new USBPort("/dev/ttyACM0");
        port.openPort();
    }

    @After
    public void close() {
        port.closePort();
    }

    @Test
    public void testLeftRight() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 30, 2, 47};
        byte[] buf2 = new byte[]{3, 1, 30, 0, 99};

        Thread.sleep(1000);
        port.writeData(buf1);
        port.readResponse();
        Thread.sleep(1000);
        port.writeData(buf2);
        port.readResponse();

    }

    @Test
    public void checkBaudrate() throws SerialPortException, InterruptedException {
        byte[] buf3 = new byte[]{2, 1, 4, 0, 0};

        port.writeData(buf3);
        Thread.sleep(200);
        String baudrate = port.readResponse();
        assertEquals("3", baudrate);
    }

    @Test
    public void checkModelNumber() throws SerialPortException, InterruptedException {
        byte[] buf3 = new byte[]{4, 1, 0, 0, 0};

        port.writeData(buf3);
        Thread.sleep(200);
        String baudrate = port.readResponse();
        assertEquals("350", baudrate);

    }

    @Test
    public void testLEDs() throws SerialPortException, InterruptedException {
        byte[] WHITE = new byte[]{1, 1, 25, 7, 0};
        byte[] GREEN = new byte[]{1, 1, 25, 3, 0};
        byte[] OFF =   new byte[]{1, 1, 25, 0, 0};

        port.writeData(WHITE);
        port.readResponse();
        Thread.sleep(1000);

        port.writeData(GREEN);
        port.readResponse();
        Thread.sleep(1000);

        port.writeData(OFF);
        port.readResponse();
    }

    @Test
    public void testLEDsFast() throws SerialPortException, InterruptedException {
        byte[] WHITE = new byte[]{1, 1, 25, 7, 0};
        byte[] GREEN = new byte[]{1, 1, 25, 3, 0};
        byte[] OFF =   new byte[]{1, 1, 25, 0, 0};
        String resp;

        for(int i=0;i<100;i++) {
            port.writeData(WHITE);
            resp = port.readResponse();
            assertEquals("7",resp);
            port.writeData(GREEN);
            resp = port.readResponse();
            assertEquals("3",resp);
            port.writeData(OFF);
            resp = port.readResponse();
            assertEquals("0", resp);
        }
    }


}
