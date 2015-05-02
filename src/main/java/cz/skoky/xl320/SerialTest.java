package cz.skoky.xl320;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Created by skokan on 30.4.15.
 */
public class SerialTest {

        public static void main(String[] args) {
            String[] portNames = SerialPortList.getPortNames();
            for(int i = 0; i < portNames.length; i++){
                System.out.println(portNames[i]);
            }

            SerialPort serialPort = new SerialPort("/dev/ttyACM0");
            try {
                serialPort.openPort();//Open serial port
                serialPort.setParams(115200, 8, 1, 0);//Set params.

                while(true) {
                    // byte[] buffer; // = serialPort.readBytes(100);//Read 10 bytes from serial port
                    // String word = serialPort.readString();
                    byte[] buffer = serialPort.readBytes(5);
                    if (buffer!=null)
                        if (buffer.length>0)
                            System.out.println("Data:"+ buffer.length+ ":" + new String(buffer));
                }

            }
            catch (SerialPortException ex) {
                System.out.println(ex);
            } finally {
                try {
                    serialPort.closePort();//Close serial port
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }

        }

}
