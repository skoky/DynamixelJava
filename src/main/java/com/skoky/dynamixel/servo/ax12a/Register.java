package com.skoky.dynamixel.servo.ax12a;

/**
 * List of registers with addresses nd limits. Based on e-manual http://support.robotis.com/en/product/dynamixel/xl-series/xl-320.htm
 *
 * Created by skokan on 25.4.15.
 */
public enum Register {

    // TODO review!

    MODEL_NUMBER        (0x0, 2, true, 12),
    FIRMWARE_VERSION    (0x2, 1, true, null),
    ID                  (0x3, 1, false, 1),
    BAUD_RATE           (0x4, 1, false, 1),
    DELAY_TIME          (0x5, 1, false, 250),
    CW_ANGLE_LIMIT      (0x6, 2, false, 0),
    CCW_ANGLE_LIMIT     (0x8, 2, false, 1023),
//    CONTROL_MDE         (0x11, 1, false, 2),
    LIMIT_TEMPERATURE   (0x11, 1, false, 70),
    LOWER_LIMIT_VOLTAGE (0x12, 1, false, 60),
    UPPER_LIMIT_VOLTAGE (0x13, 1, false, 140),
    MAX_TORQUE          (0x14, 2, false, 1023),
    STATUS_RETURN_LEVEL (0x16, 1, false, 2),
    ALARM_LED           (0x17, 1, false, 36),
    SHUTDOWN_ALARM      (0x18, 1, false, 36),
    TORQUE_ENABLE       (0x24, 1, false, 0),
    LED_ON_OFF          (0x25, 1, false, 0),
    CW_COMPLIANCE_MARGIN(0x26, 1, false, 1),
    CCW_COMPLIANCE_MARGIN(0x27,1, false, 1),
    CW_COMPLIANCE_SLOPE (0x28, 1, false, 32),
    CCW_COMPLIANCE_SLOPE(0x29, 1, false, 32),
    GOAL_POSITION       (0x30, 2, false, null),
    MOVING_SPEED        (0x32, 2, false, null),
    GOAL_TORQUE         (0x34, 2, false, null),
    CURRENT_POSITION    (0x36, 2, true, null),
    PRESENT_SPEED       (0x38, 2, true, null),
    PRESENT_LOAD        (0x40, 2, true, null),
    PRESENT_VOLTAGE     (0x42, 1, true,null),
    PRESENT_TEMPERATURE (0x43, 1, true, null),
    REGISTERED_INSTR    (0x44, 1, true, 0),
    MOVING              (0x46, 1, true, 0),
    LOCK_EEPROM         (0x47, 1, false,0),
    PUNCH               (0x48, 2, false,32);

    private final int address;
    private final int size;
    private final boolean readonly;
    private final Integer initValue;

    /**
     *
     * @param address
     * @param size
     * @param readonly
     * @param initValue
     */
    Register(int address, int size, boolean readonly, Integer initValue) {
        this.address = address;
        this.size = size;
        this.readonly = readonly;
        this.initValue = initValue;
    }

    public int getAddress() {
        return address;
    }

    public int getSize() {
        return size;
    }

    public boolean isReadOnly() {
        return readonly;
    }
}
