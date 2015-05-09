package com.skoky.dynamixel.port;

import jtermios.*;

import java.util.Arrays;
import java.util.List;

import static jtermios.JTermios.*;
/**
 * Created by skokan on 7.5.15.
 */
public class PortLinux implements SerialPort {

    @Override
    public void open(String portName) {
        System.out.println("Port list:" + Arrays.toString(JTermios.getPortList().toArray()));
        int fd = JTermios.open(portName, O_RDWR | O_NOCTTY | O_NONBLOCK);
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

    }

    @Override
    public List<String> getPortsList() {
        return JTermios.getPortList();
    }
}
