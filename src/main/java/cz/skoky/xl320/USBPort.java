package cz.skoky.xl320;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by skokan on 29.4.15.
 */
public class USBPort {

    private SerialPort serialPort;

    public USBPort(String port) {
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("Available ports:" + Arrays.toString(portNames));
    }

    public void openPort() throws SerialPortException {
        serialPort = new SerialPort("/dev/ttyACM0");
        serialPort.openPort();//Open serial port
        serialPort.setParams(115200, 8, 1, 0);//Set params.
    }

    public Integer readResponse(int length) {
        try {

            byte[] buffer = serialPort.readBytes(length);
            if (buffer != null)
                if (buffer.length > 0) {
                    System.out.println("Data:" + buffer.length + ":" + Arrays.toString(buffer));
                    System.out.println("String:" + new String(buffer, "US-ASCII"));
                    ByteBuffer bb = ByteBuffer.wrap(buffer);
                    if (length==1)
                        return Integer.valueOf(bb.get());
                    else
                        return Integer.valueOf(bb.getShort());
                } else {
                    throw new IllegalStateException("No data received");
                }

        } catch (SerialPortException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeData(byte[] buf) throws SerialPortException {
//        if (buf.length != 5) throw new IllegalArgumentException("Buffer != 5 ");
        System.out.println("Sending to servo:"+Arrays.toString(buf));
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
