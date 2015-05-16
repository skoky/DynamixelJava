package com.skoky.dynamixel.raw;

import com.skoky.dynamixel.err.ErrorResponseException;
import com.skoky.dynamixel.err.ResponseParsingException;
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
        buffer[3]= params.length+2;        // length
        buffer[4]= 0x3;          // WRITE_DATA
        for(int i=0;i<params.length;i++) {
            buffer[5+i] = params[i];
        }
        buffer[5+params.length] = crc(buffer,params);    // CRC
        return toByteArray(buffer);
    }

    @Override
    public byte[] buildReadData(int servoId, int... params) {
        int[] buffer = new int[6+params.length];
        buffer[0]= 0xFF; //header
        buffer[1]= 0xFF; //header
        buffer[2]= servoId; //servo ID
        buffer[3]= 0x02+params.length;            // length
        buffer[4]= 0x2;          // WRITE_DATA
        for(int i=0;i<params.length;i++) {
            buffer[5+i] = params[i];
        }
        buffer[5+params.length] = crc(buffer,params);    // CRC
        return toByteArray(buffer);
    }

    @Override
    public List<Data> parse(byte[] p) throws ResponseParsingException {
        if (p==null || p.length==0) throw new ResponseParsingException("Null data");
        if (p[0]!=(byte)0xFF || p[1]!=(byte)0xFF) throw new ResponseParsingException("Not starting with 0xFF");
        Data data = new Data(TYPES.NONE_V1);    // packet name not defined in V1
        data.servoId=p[2];
        data.error = p[4];
        int length = p[3];
        int sum = data.servoId + length + data.error;
        data.params = new int[(length-2)];
        for(int i=5;i<(5+length-2);i++) {
            data.params[i-5]=Byte.toUnsignedInt(p[i]);
            sum+=data.params[i-5];
        }
        int calcCrc = (255 - ((sum) % 256));
//        calcCrc = crcResponse(p);
        int crc = Byte.toUnsignedInt(p[length+3]);
        if ( crc !=calcCrc)
            throw new ResponseParsingException("CRC not the same! Calculated:"+calcCrc + " expected:" + crc);
        if (data.params.length>0) data.result=data.params[0];
        if (data.params.length==2) data.result+=data.params[1]*256;
        List list = new ArrayList<Data>();
        list.add(data);
        return list;
    }

    @Override
    public Data parseFirst(byte[] p) throws ResponseParsingException, ErrorResponseException {
        List<Data> d = parse(p);
        Data one = d.get(0);
        if (one.error!=0) throw new ErrorResponseException(one.error);
        return one;
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
