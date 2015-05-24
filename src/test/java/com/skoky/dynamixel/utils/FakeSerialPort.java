package com.skoky.dynamixel.utils;

import com.skoky.dynamixel.err.SerialLinkError;
import com.skoky.dynamixel.port.SerialPort;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skoky on 20.5.15.
 */
public class FakeSerialPort implements SerialPort {


    private final Map<String, String> responses;

    public FakeSerialPort(String filename) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(getClass().getResource(filename).getFile()), StandardCharsets.UTF_8);
        responses = new HashMap<>(0);
        for(String line: lines){
            String[] x = line.split("->");
            responses.put(x[0],x[1]);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public byte[] sendAndReceive(byte[] p) throws SerialLinkError {
        String str = Hex.encodeHexString(p);
        try {
            String resp = responses.get(str);
            if (resp==null)
                throw new IllegalStateException("No response for "+str);
            else
                return Hex.decodeHex(resp.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialLinkError {
        return sendAndReceive(p);
    }

    @Override
    public void setRecordFile(String s) {

    }

    @Override
    public void send(byte[] request) {

    }
}
