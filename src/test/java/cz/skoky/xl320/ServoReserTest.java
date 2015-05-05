package cz.skoky.xl320;

import jssc.SerialPortException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Testing communication with OpenCM. This only works with OpenCM connected to /dev/ttyACM0 and servo on ID 1
 * Created by skokan on 30.4.15.
 */
public class ServoReserTest {

    private USBPort port;
    private boolean portOpen;

    @Before
    public void setUp() throws SerialPortException {
        port = new USBPort("/dev/ttyACM0");
        portOpen = port.openPort();
    }

    @After
    public void close() {
        if (portOpen)
            port.closePort();
    }

    @Test
    public void testAll() throws SerialPortException, InterruptedException {
        testSetMaxTorque();
        testCleanStatus();
        testGetStatus();
        testPresentPosition();
        testLeftRight();
    }
    @Test
    public void testHWErrorStatus() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{2, 1, 50, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        assertEquals("0", port.readResponse());

    }


    @Test
    public void testSetMaxTorque() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 15, 1, 1};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();

    }

    @Test
    public void testCleanStatus() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{1, 1, 18, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }


    @Test
    public void testGetTorqueEnable() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{2, 1, 24, 0, 0};
        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }


    @Test
    public void testGetMaxTorque() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 15, 0, 0};
        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }



    @Test
    public void testSetTorqueEnable() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{1, 1, 24, 1, 0};
        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }
    @Test
    public void testGetStatus() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 41, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }

    @Test
    public void testPresentLoad() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 41, 0, 0};
        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }

    @Test
    public void testResetPresentLoad() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 41, 0, 0};
        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }


    @Test
    public void testPresentPosition() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 37, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        System.out.println("Present position:" + port.readResponse());
    }

    @Test
    public void testPresentSpeed() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 39, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }

    @Test
    public void testGoalSpeed() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{4, 1, 32, 0, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }

    @Test
    public void testGoalSpeedLow() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 32, 1, 0};

        port.writeData(buf1);
        Thread.sleep(200);
        port.readResponse();
    }


    @Test
    public void testLeftRight() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 30, 1, 70};
        byte[] buf2 = new byte[]{3, 1, 30, 2, 40};


        port.writeData(buf1);
        Thread.sleep(500);
        port.readResponse();
        Thread.sleep(2000);
//        port.writeData(buf2);
//        port.readResponse();
//        Thread.sleep(2000);

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



}
