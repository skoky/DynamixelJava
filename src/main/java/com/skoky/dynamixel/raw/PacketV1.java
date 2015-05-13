package com.skoky.dynamixel.raw;

import java.util.ArrayList;
import java.util.List;

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
    public byte[] buildPing(int servoId) {
        int[] buffer = new int[6];
        buffer[0]= 0xFF; //header
        buffer[1]= 0xFF; //header
        buffer[2]= servoId; //servo ID
        buffer[3]=2;            // length
        buffer[4]=0x1;          // PING
        buffer[5] = crc(buffer);    // CRC
        return toByteArray(buffer);
    }

    @Override
    public byte[] buildWriteData(int servoId, int... params) {
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
    public byte[] buildReadData(int servoId, int... params) {
        return new byte[0];
    }

    @Override
    public List<PacketV2.Data> parse(byte[] p) {
        if (p==null || p.length==0) return null;
        if (p[0]!=(byte)0xFF || p[1]!=(byte)0xFF) throw new IllegalArgumentException("Not starting with 0xFF");
        Data data = new Data(TYPES.PING);
        data.error = p[4];
        int length = p[3];
        data.params = new int[(length-2)];
        for(int i=5;i<(length-2);i++) {
            data.params[i-5]=p[i];
        }
        data.servoId=p[2];
        int sum = data.servoId + length;
        int calcCrc = (255 - ((sum) % 254));
        if ( p[length+3]!=(byte)calcCrc) System.out.println("CRC not the same!");
        List list = new ArrayList<Data>();
        list.add(data);
        return list;
    }

    private int crc(int[] buffer, int... params) {
        int sum = buffer[2];
        sum +=buffer[3];
        sum +=buffer[4];
        for(int i=0;i<params.length;i++) {
            sum += params[i];
        }
        int crc = (255 - ((sum) % 254));
        return crc;

    }
}
