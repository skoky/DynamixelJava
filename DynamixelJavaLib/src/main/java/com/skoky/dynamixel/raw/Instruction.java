package com.skoky.dynamixel.raw;

/**
 * Created by skoky on 23.5.15.
 */

public enum Instruction {
    PING(1, true),
    READ(2, true),
    WRITE(3, true),
    REG_WRITE(4, true),
    ACTION(5, true),
    FACTORY_RESET(6, true),
    REBOOT(8, false),
    STATUS(0x55, false),
    SYNC_READ(0x82, false),
    SYNC_WRITE(0x83, true),
    BULK_READ(0x92, false),
    BULK_WRITE(0x93, false);

    private final int id;
    private final boolean v1support;

    Instruction(int id, boolean v1support) {
        this.id = id;
        this.v1support = v1support;
    }

    public int getId() {
        return id;
    }
}


