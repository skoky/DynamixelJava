package cz.skoky.xl320;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by skokan on 29.4.15.
 */
public class USBPort {

    private SerialPort serialPort;
    private byte[] buffer = new byte[256];

    public USBPort(String port) {
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("Available ports:" + Arrays.toString(portNames));
    }

    public void openPort() throws SerialPortException {
        serialPort = new SerialPort("/dev/ttyACM0");
        serialPort.openPort();//Open serial port
        serialPort.setParams(115200, 8, 1, 0);//Set params.
    }

    public String readResponse() {
        int i = 0;
        try {
            while (true) {
                if (i > buffer.length) throw new ArrayIndexOutOfBoundsException("Buffer overflow");
                buffer[i] = serialPort.readBytes(1, 2000)[0];
                if (i > 0) {
                    if (buffer[i] == 10 && buffer[i-1] == 13) break;  // end of message by CR & LF
                }
                i++;
            }
            i++;
            byte[] resultBuffer= Arrays.copyOf(buffer, i);
            String result = new String(Arrays.copyOf(resultBuffer,i), "US-ASCII");
            System.out.print("Received:" + resultBuffer.length + ":" + Arrays.toString(resultBuffer) + " / " + result);
            return result;
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SerialPortTimeoutException e) {
            try {
                System.out.println("Data so far:"+new String(buffer,"US-ASCII"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void writeData(byte[] buf) throws SerialPortException {
//        if (buf.length != 5) throw new IllegalArgumentException("Buffer != 5 ");
        System.out.println("Sending to servo:" + Arrays.toString(buf));
        serialPort.writeBytes(buf);
    }

    public void closePort() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
