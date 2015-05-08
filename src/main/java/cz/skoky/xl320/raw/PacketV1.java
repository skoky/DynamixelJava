package cz.skoky.xl320.raw;

import java.nio.ByteBuffer;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1 implements Packet {
    @Override
    public byte[] buildPing() {
        int[] buffer = new int[8];
        buffer[0]= 0xFF; //header
        buffer[1]= 0xFE; //servo ID
        buffer[2]=2;            // length
        buffer[3]=0x1;          // PING
        buffer[4] = crc(buffer);    // CRC
        return toByteArray(buffer);
    }

    private byte[] toByteArray(int[] buffer) {
        byte[] x = new byte[buffer.length];
        for(int i=0; i<buffer.length;i++) {
            x[i]= (byte) buffer[i];
        }
        return x;
    }

    @Override
    public byte[] buildWriteDate(int servoId, int p1, int p2, int p3) {
        int[] buffer = new int[8];
        buffer[0]= 0xFF; //header
        buffer[1]= servoId; //servo ID
        buffer[2]= 0x05;            // length
        buffer[3]= 0x3;          // WRITE_DATA
        buffer[4]= p1;
        buffer[5]= p2;
        buffer[6]= p3;
        buffer[7] = crc(buffer);    // CRC
        return toByteArray(buffer);
    }


    private int crc(int[] buffer, int... params) {
        int sum = buffer[1];
        sum +=buffer[2];
        sum +=buffer[3];
        sum +=buffer[4];
        sum +=buffer[5];
        sum +=buffer[6];

        return sum&0xFF;
    }
}
