package cz.skoky.xl320.raw;

/**
 * Created by skokan on 7.5.15.
 */
public interface Packet {

    byte[] buildPing();
    byte[] buildWriteDate(int servoId, int p1, int p2, int p3);
}
