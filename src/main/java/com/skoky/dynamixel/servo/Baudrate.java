package com.skoky.dynamixel.servo;

/**
 * Created by skokan on 14.5.15.
 */
public enum Baudrate {
    B1000000(1),
    B500000(3),
    B400000(4),
    B250000(7),
    B200000(9),
    B117647(16),
    B57142(34),
    B19230(103),
    B9615(207);

    private final int id;

    Baudrate(int id) {
        this.id=id;
    }
}
