package cz.skoky.xl320;

import java.nio.ByteBuffer;

/**
 * Created by skokan on 29.4.15.
 */
public class UsbSender {

    public UsbSender(String openCMPort) {

    }

    public boolean send(ByteBuffer bb) {
        return true;
    }

    public byte[] waitForResponse() {
        return new byte[]{1,2};
    }

}
