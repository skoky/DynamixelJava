package com.skoky.dynamixel.port;

import com.skoky.dynamixel.err.SerialLinkError;
import org.apache.commons.codec.binary.Hex;
import purejavacomm.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skoky on 23.5.15.
 */
public class SerialPortImpl implements SerialPort {
    private static final long DEFAULT_READ_TIMEOUT = 100;  // timeout in ms to wait for servos response
    private static Logger log = Logger.getGlobal();
    private PureJavaSerialPort port;
    private String recordFileName;
    private FileWriter logFile;

    protected SerialPortImpl(String serialPortName) throws SerialPortException {
        this(serialPortName,1000000);
        log.setLevel(Level.ALL);
    }

    protected SerialPortImpl(String serialPortName, Integer speed) throws SerialPortException {
        CommPortIdentifier portI;
        try {
            portI = CommPortIdentifier.getPortIdentifier(serialPortName);
        } catch (NoSuchPortException e) {
            throw new SerialPortException(e.getClass().getName()+". Available ports:"+Arrays.toString(getAllPorts().toArray()));
        }
        try {
            port = (PureJavaSerialPort) portI.open("Dynamixel", 2000);
        } catch (PortInUseException e) {
            throw new SerialPortException(e.getMessage() + ". Current lock owner:"+portI.getCurrentOwner());
        }
        log.info("Port open");
        if (speed != null)
            try {
                port.setSerialPortParams(speed, purejavacomm.SerialPort.DATABITS_8, purejavacomm.SerialPort.STOPBITS_1, purejavacomm.SerialPort.PARITY_NONE);
            } catch (UnsupportedCommOperationException e) {
                throw new SerialPortException("Unable to set port speed. "+e.getMessage());
            }

    }

    private List<String> getAllPorts() {
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        List<String> r = new ArrayList<String>();
        while(e.hasMoreElements()) {
            CommPortIdentifier p = (CommPortIdentifier) e.nextElement();
            r.add(p.getName());
        }
        return r;
    }

    public void close() {
        if (port != null) port.close();
        if (logFile!=null) try {
            logFile.close();
        } catch (IOException e) {
            log.severe("Unable to close log file "+e.getMessage());
        }
    }
    public byte[] sendAndReceive(byte[] p) throws SerialLinkError {
        return sendAndReceive(p, DEFAULT_READ_TIMEOUT);
    }

    public void send(byte[] p) {
        sendAndReceiveLocal(p,0,true);
    }

    public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialPortException {
            return sendAndReceiveLocal(p,longSleep,false);
    }

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    byte[] b = new byte[256];
    private byte[] sendAndReceiveLocal(byte[] p, long longSleep,boolean skipResponse) {
        try {
            long start = System.currentTimeMillis();
            OutputStream out = port.getOutputStream();
            InputStream is = port.getInputStream();
            out.write(p);
            if (skipResponse) return null;
            port.enableReceiveTimeout((int) longSleep);
            buffer.rewind();
            int s=0;
            while(true) {
                int size = is.read(b);
                log.fine("Read bytes: " + size + " in " + (System.currentTimeMillis() - start) + "ms. Data:" + Hex.encodeHexString(b));
                if (size > 0) {
                    if (s+size>buffer.capacity()) log.severe("Reading failed because of invalid data on serial port");
                    buffer.put(b,0,size);
                    log.fine("Data:" + buffer.position() + ":" + Hex.encodeHexString(buffer.array()));
                    s+=size;
                } else break;
            }
            byte[] x = new byte[s];
            buffer.rewind(); buffer.get(x);
            log.fine("Returning:" + Hex.encodeHexString(x));
            appendToLogFile(p,x);
            return x;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerialPortException(e.getClass().getName()+":"+e.getMessage());

        }
    }

    private void appendToLogFile(byte[] request, byte[] response) {
        if (logFile==null) return;
        String msg = Hex.encodeHexString(request) + "->" + Hex.encodeHexString(response) + "\n";
        try {
            logFile.append(msg);
        } catch (IOException e) {
            log.severe("Unable to append to log file");
        }
    }

    public void setRecordFile(String recordingFileName) {
        this.recordFileName=recordingFileName;
        try {
            this.logFile = new FileWriter(recordingFileName,true);
        } catch (IOException e) {
            log.severe("Unable to serial link log file " + recordingFileName);
        }

    }

    @Override
    public String toString() {
        return "SerialPortImpl{" +
                "port=" + port.getName() + '}';
    }
}
