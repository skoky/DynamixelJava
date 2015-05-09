package com.skoky.dynamixel.raw;

/**
 * Created by skokan on 7.5.15.
 */
public class PacketV1 extends PacketCommon implements Packet {
    @Override
    public byte[] buildPing() {
        int[] buffer = new int[6];
        buffer[0]= 0xFF; //header
        buffer[1]= 0xFF; //header
        buffer[2]= 0xFE; //servo ID
        buffer[3]=2;            // length
        buffer[4]=0x1;          // PING
        buffer[5] = crc(buffer);    // CRC
        return toByteArray(buffer);
    }

    @Override
    public byte[] buildWriteDate(int servoId, int... params) {
        int[] buffer = new int[6+params.length];
        buffer[0]= 0xFF; //header
        buffer[1]= 0xFF; //header
        buffer[2]= servoId; //servo ID
        buffer[3]= 0x05;            // length
        buffer[4]= 0x3;          // WRITE_DATA
        for(int i=0;i<params.length;i++) {
            buffer[5+i] = params[i];
        }
        buffer[5+params.length] = crc(buffer,params);    // CRC
        return toByteArray(buffer);
    }

    @Override
    public void parse(byte[] p) {
        if (p[0]!=0xFF) throw new IllegalArgumentException("Not starting with 0xFF");


    }


    private int crc(int[] buffer, int... params) {
        int sum = buffer[2];
        sum +=buffer[3];
        sum +=buffer[4];
        for(int i=0;i<params.length;i++) {
            sum += params[i];
        }
        int crc = (255 - ((sum) % 256));
        return crc;

    }
}
