package cz.skoky.xl320.port;

import com.sun.jna.Platform;

/**
 * Created by skokan on 29.4.15.
 */
public class USBPort {


    public USBPort(String s) {

    }

    public static void main(String [] args) {
        if (Platform.isLinux()) {
            new PortLinux();
        }
    }

    public boolean openPort() {
        return false;
    }

    public void closePort() {
    }

    public void writeData(byte[] buf1) {

    }

    public String readResponse() {

        return null;
    }
}
