package com.skoky.dynamixel.port;

import jtermios.*;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static jtermios.JTermios.*;
/**
 * Created by skokan on 7.5.15.
 */
public class PortLinux implements SerialPort {

    private final int fd;
    private final String portName;

    PortLinux(String portName) {
        this.portName = portName;
        fd = JTermios.open(portName, O_RDWR | O_NOCTTY | O_NONBLOCK);
        if (fd == -1)
            throw new RuntimeException("Unale to open port "+portName);
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

        cfsetispeed(opts, B9600);
        cfsetospeed(opts, B9600);

        tcsetattr(fd, TCSANOW, opts);

        tcflush(fd, TCIOFLUSH);
    }

    @Override
    public void close() {
        JTermios.close(fd);
    }

    private byte[] buffer = new byte[1024];
    private ByteBuffer buffer2 = ByteBuffer.allocate(1024);
    @Override
    public byte[] sendAndReceive(byte[] data) {
        int result = JTermios.write(fd, data, data.length);
        System.out.println("Writing :" + Hex.encodeHexString(data) + " size:" + result);
        if (result==-1) throw new IllegalStateException("Serial port not useful. Port:"+portName);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer2.rewind();
        while(true) {
            result = JTermios.read(fd, buffer, buffer.length);
            if (result>0) {
                buffer2.put(buffer,0,result);
            } else break;
        }
        int size = buffer2.position();
        byte[] resultB=new byte[size];
        buffer2.rewind();
        buffer2.get(resultB, 0, size);
        System.out.println("Read result size:" + size);
        System.out.println("Received:"+Hex.encodeHexString(resultB));
        return resultB;
    }

}
