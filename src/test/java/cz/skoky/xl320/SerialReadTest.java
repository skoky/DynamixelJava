package cz.skoky.xl320;

import jssc.SerialPortException;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by skokan on 30.4.15.
 */
public class SerialReadTest {

    @Test
    public void testLeftRight() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 30, 2, 47};
        byte[] buf2 = new byte[]{3, 1, 30, 0, 99};
        USBPort port = new USBPort("/dev/ttyACM0");
        port.openPort();

        Thread.sleep(1000);
        port.writeData(buf1);
        port.readResponse();
        Thread.sleep(1000);
        port.writeData(buf2);
        port.readResponse();

        port.closePort();
    }

    @Test
    public void testSetAndCheck() throws SerialPortException, InterruptedException {
        byte[] buf1 = new byte[]{3, 1, 30, 2, 47};
        byte[] buf2 = new byte[]{3, 1, 30, 0, 99};
        byte[] buf3 = new byte[]{2, 1, 4, 0, 0};
        USBPort port = new USBPort("/dev/ttyACM0");
        port.openPort();

        port.writeData(buf1);
        port.readResponse();
        Thread.sleep(1000);

        port.writeData(buf3);
        port.readResponse();
        Thread.sleep(1000);

//        port.writeData(buf2);
//        port.readResponse();
//        Thread.sleep(1000);

        port.closePort();
    }

    @Test
    public void test2() {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.putShort((short) 559);
        System.out.println("Array:" + Arrays.toString(bb.array()));
    }
}
