package cz.skoky.xl320;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

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

    public Integer readResponse() {
        try {

            byte[] buffer = serialPort.readBytes(5);
            if (buffer != null)
                if (buffer.length > 0) {
                    System.out.println("Data:" + buffer.length + ":" + Arrays.toString(buffer));
                    ByteBuffer bb = ByteBuffer.wrap(buffer);
                    bb.rewind();
                    return Integer.valueOf(bb.getShort(3));
                } else {
                    throw new IllegalStateException("No data received");
                }

        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void writeData(byte[] buf) throws SerialPortException {
        if (buf.length != 5) throw new IllegalArgumentException("Buffer != 5 ");
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
