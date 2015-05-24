package com.skoky.dynamixel.port;

import com.skoky.dynamixel.err.SerialLinkError;
import jtermios.Termios;
import purejavacomm.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by skoky on 23.5.15.
 */
public class SerialPortImpl implements SerialPort {
    private static final int TOUT = 2000;
    private static final long DEFAULT_SLEEP = 20;
    Logger log = Logger.getGlobal();
    private PureJavaSerialPort port;
    private String recordFile;

    protected SerialPortImpl(String serialPortName) throws SerialPortException {
        this(serialPortName,1000000);
    }

    protected SerialPortImpl(String serialPortName, Integer speed) throws SerialPortException {
        CommPortIdentifier portI = null;
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
        try {
            port.enableReceiveTimeout(TOUT);
        } catch (UnsupportedCommOperationException e) {
            throw new SerialPortException(e.getMessage());
        }
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
    }
    public byte[] sendAndReceive(byte[] p) throws SerialLinkError {
        return sendAndReceive(p, DEFAULT_SLEEP);
    }
    public byte[] sendAndReceive(byte[] p, long longSleep) throws SerialPortException {
        byte[] b = new byte[1024];
        try {
            long start = System.currentTimeMillis();
            OutputStream out = port.getOutputStream();
            InputStream is = port.getInputStream();
            out.write(p);
            Thread.sleep(longSleep);
            int size = is.read(b);
            log.fine("Read bytes: " + size + " in " + (System.currentTimeMillis() - start) + "ms");
            if ((System.currentTimeMillis()-start)>TOUT)
                log.severe("Communication timeout over serial port!");
            if (size > 0) {
                byte[] buffer2 = Arrays.copyOfRange(b, 0, size);
                return buffer2;
            }

        } catch (Exception e) {
            throw new SerialPortException(e.getMessage());
        }
        return null;

    }

    public void setRecordFile(String recordingFileName) {
        this.recordFile=recordingFileName;
    }
}
