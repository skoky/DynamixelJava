package com.skoky.dynamixel.servo.xl320;

/**
 * List of registers with addresses nd limits. Based on e-manual http://support.robotis.com/en/product/dynamixel/xl-series/xl-320.htm
 *
 * Created by skokan on 25.4.15.
 */
public enum Register {

    MODEL_NUMBER        (0x0, 2, true, 350, null, null),
    FIRMWARE_VERSION    (0x2, 1, true, null, null, null),
    ID                  (0x3, 1, false, 1, 0, 252),
    BAUD_RATE           (0x4, 1, false, 3, 0, 3),
    DELAY_TIME          (0x5, 1, false, 250, 0, 254),
    CW_ANGLE_LIMIT      (0x6, 2, false, 0, 0, 1023),
    CCW_ANGLE_LIMIT     (0x8, 2, false, 1023, 0, 1023),
    CONTROL_MDE         (0x11, 1, false, 2, 1, 2),
    LIMIT_TEMPERATURE   (0x12, 1, false, 65, 0, 150),
    LOWER_LIMIT_VOLTAGE (0x13, 1, false, 60, 50, 250),
    UPPER_LIMIT_VOLTAGE (0x14, 1, false, 90, 50, 250),
    MAX_TOURGE          (0x15, 2, false, 1023, 0, 1023),
    RETURN_LEVEL        (0x17, 1, false, 2, 0, 2),
    SHUTDOWN_ALARM      (0x18, 1, false, 36, 0, 127),
    TORQUE_ENABLE       (0x24, 1, false, 0, 0, 1),
    LED_ON_OFF          (0x25, 1, false, 0, 0, 7),
    D_GAIN              (0x27, 1, false, 0, 0, 254),
    I_GAIN              (0x28, 1, false, 0, 0, 254),
    P_GAIN              (0x29, 1, false, 32, 0, 1023),
    GOAL_POSITION       (0x30, 2, false, null, 0, 2047),
    GOAL_VELOCITY       (0x32, 2, false, null, 0, 1023),
    GOAL_TOURGE         (0x35, 2, false, null, 0, null),
    CURRENT_POSITION    (0x37, 2, true, null, null, null),
    PRESENT_SPEED       (0x39, 2, true, null, null, null),
    PRESENT_LOAD        (0x41, 2, true, null, null, null),
    PRESENT_VOLTAGE     (0x45, 1, true, null, null,null),
    PRESENT_TEMPERATURE (0x46, 1, true, null, null, null),
    REGISTERED_INSTR    (0x47, 1, true, 0, null, null),
    MOVING              (0x49, 1, true, 0, null, null),
    HW_ERROR_STATUS     (0x50, 1, true, 0, null, null),
    PUNCH               (0x51,2,false,32,0,1023);

    private final int address;
    private final int size;
    private final boolean readonly;
    private final Integer initValue;
    private final Integer min;
    private final Integer max;

    /**
     *
     * @param address
     * @param size
     * @param readonly
     * @param initValue
     * @param min
     * @param max
     */
    Register(int address, int size, boolean readonly, Integer initValue, Integer min, Integer max) {
        this.address = address;
        this.size = size;
        this.readonly = readonly;
        this.initValue = initValue;
        this.min = min;
        this.max = max;
    }

    public int getAddress() {
        return address;
    }

    public int getSize() {
        return size;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isReadOnly() {
        return readonly;
    }
}
