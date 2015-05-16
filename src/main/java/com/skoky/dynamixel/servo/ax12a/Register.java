package com.skoky.dynamixel.servo.ax12a;

/**
 * List of registers with addresses nd limits. Based on e-manual http://support.robotis.com/en/product/dynamixel/xl-series/xl-320.htm
 *
 * Created by skokan on 25.4.15.
 */
public enum Register {

    // TODO review!

    MODEL_NUMBER        (0, 2, true, 12),
    FIRMWARE_VERSION    (2, 1, true, null),
    ID                  (3, 1, false, 1),
    BAUD_RATE           (4, 1, false, 1),
    DELAY_TIME          (5, 1, false, 250),
    CW_ANGLE_LIMIT      (6, 2, false, 0),
    CCW_ANGLE_LIMIT     (8, 2, false, 1023),
//    CONTROL_MDE         (0x11, 1, false, 2),
    LIMIT_TEMPERATURE   (11, 1, false, 70),
    LOWER_LIMIT_VOLTAGE (12, 1, false, 60),
    UPPER_LIMIT_VOLTAGE (13, 1, false, 140),
    MAX_TORQUE          (14, 2, false, 1023),
    STATUS_RETURN_LEVEL (16, 1, false, 2),
    ALARM_LED           (17, 1, false, 36),
    SHUTDOWN_ALARM      (18, 1, false, 36),
    TORQUE_ENABLE       (24, 1, false, 0),
    LED_ON_OFF          (25, 1, false, 0),
    CW_COMPLIANCE_MARGIN(26, 1, false, 1),
    CCW_COMPLIANCE_MARGIN(27,1, false, 1),
    CW_COMPLIANCE_SLOPE (28, 1, false, 32),
    CCW_COMPLIANCE_SLOPE(29, 1, false, 32),
    GOAL_POSITION       (30, 2, false, null),
    MOVING_SPEED        (32, 2, false, null),
    GOAL_TORQUE         (34, 2, false, null),
    CURRENT_POSITION    (36, 2, true, null),
    PRESENT_SPEED       (38, 2, true, null),
    PRESENT_LOAD        (40, 2, true, null),
    PRESENT_VOLTAGE     (42, 1, true,null),
    PRESENT_TEMPERATURE (43, 1, true, null),
    REGISTERED_INSTR    (44, 1, true, 0),
    MOVING              (46, 1, true, 0),
    LOCK_EEPROM         (47, 1, false,0),
    PUNCH               (48, 2, false,32);

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
