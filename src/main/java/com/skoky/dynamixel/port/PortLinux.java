package com.skoky.dynamixel.port;


import com.skoky.dynamixel.err.SerialLinkError;
import jtermios.*;
import org.apache.commons.codec.binary.Hex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

import static jtermios.JTermios.*;

/**
 * Created by skokan on 7.5.15.
 */
public class PortLinux implements SerialPort {

    private FileWriter f;
    Logger log = Logger.getGlobal();
    private final int fd;
    private final String portName;

    public void setRecordFile(String logFileName) throws SerialLinkError {
        try {
            f = new FileWriter(logFileName,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PortLinux(String portName) throws SerialLinkError {
        this.portName = portName;
        fd = JTermios.open(portName, O_RDWR | O_NOCTTY | O_NONBLOCK);
        if (fd == -1)
            throw new SerialLinkError("Unable to open port " + portName);
        fcntl(fd, F_SETFL, 0);

        Termios opts = new Termios();
        tcgetattr(fd, opts);
        opts.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);
        opts.c_cflag |= (CLOCAL | CREAD);
        opts.c_cflag &= ~PARENB;
        opts.c_cflag |= CSTOPB;
        opts.c_cflag &= ~CSIZE;
        opts.c_cflag |= CS8;
        opts.c_oflag &= ~OPOST;
        opts.c_iflag &= ~INPCK;
        opts.c_iflag &= ~(IXON | IXOFF | IXANY);
        opts.c_cc[VMIN] = 0;
        opts.c_cc[VTIME] = 10;

//        cfsetispeed(opts, B230400);
//        cfsetospeed(opts, B230400);
//        setspeed(fd, opts, 1000000);

        tcsetattr(fd, TCSANOW, opts);

        tcflush(fd, TCIOFLUSH);


    }

    @Override
    public void close() {
        JTermios.close(fd);
        if (f!=null) try {
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] buffer = new byte[1024];
    private ByteBuffer buffer2 = ByteBuffer.allocate(4096);

    @Override
    public byte[] sendAndReceive(byte[] data) throws SerialLinkError {
        return sendAndReceive(data,20);
    }
    @Override
    public byte[] sendAndReceive(byte[] data, long sleep) throws SerialLinkError {
        long start=System.currentTimeMillis();
        int result = JTermios.write(fd, data, data.length);
        log.fine("Serial writing:" + Hex.encodeHexString(data) + " size:" + result);
        if (result == -1) throw new SerialLinkError("Serial port not useful. Port:" + portName);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer2.rewind();
        while (true) {
            result = JTermios.read(fd, buffer, buffer.length);
            if (result > 0) {
//                System.out.println("Adding:"+result + " -> " + Hex.encodeHexString(buffer));
                buffer2.put(buffer, 0, result);
            } else break;
            if (result<buffer.length) break;
        }
        log.fine("Response time (ms):"+ (System.currentTimeMillis()-start));
        int size = buffer2.position();
        byte[] resultB = new byte[size];
        buffer2.rewind();
        buffer2.get(resultB, 0, size);
        log.fine("Serial read (" + size + "): " + Hex.encodeHexString(resultB));
        if (f != null)
            try {
                f.append(Hex.encodeHexString(data) + "->" + Hex.encodeHexString(resultB)+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        return resultB;
    }

    @Override
    public String toString() {
        return "PortLinux{" +
                ", portName='" + portName + '\'' +
                '}';
    }
}
