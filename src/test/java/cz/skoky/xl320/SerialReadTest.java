package cz.skoky.xl320;

import jssc.SerialPortException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
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
    public void testSerialReading() {

    }
}
