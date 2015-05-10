package com.skoky.dynamixel.port;

import jtermios.*;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

import static jtermios.JTermios.*;
/**
 * Created by skokan on 7.5.15.
 */
public class PortLinux implements SerialPort {

    private final int fd;

    PortLinux(String portName) {
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
    @Override
    public byte[] sendAndReceive(byte[] data) {
        int result = JTermios.write(fd, data, data.length);
        System.out.println("Writing :"+ Hex.encodeHexString(data) + " size:"+result);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = JTermios.read(fd,buffer,buffer.length);
        System.out.println("Read result:"+result);
        return Arrays.copyOfRange(buffer,0,result);
    }

}
