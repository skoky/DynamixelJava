package com.skoky.dynamixel.raw;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1 implements Packet {
    @Override
    public byte[] buildPing() {
        int[] buffer = new int[5];
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
    public byte[] buildWriteDate(int servoId, int... params) {
        int[] buffer = new int[8];
        buffer[0]= 0xFF; //header
        buffer[1]= servoId; //servo ID
        buffer[2]= 0x05;            // length
        buffer[3]= 0x3;          // WRITE_DATA
        for(int i=0;i<params.length;i++) {
            buffer[4+i] = params[i];
        }
        buffer[4+params.length] = crc(buffer,params);    // CRC
        return toByteArray(buffer);
    }


    private int crc(int[] buffer, int... params) {
        int sum = buffer[1];
        sum +=buffer[2];
        sum +=buffer[3];
        for(int i=0;i<params.length;i++) {
            sum += params[i];
        }
        int crc = (255 - ((sum) % 256));
        return crc;

    }
}
