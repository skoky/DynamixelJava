package cz.skoky.xl320;

/**
 * List of registers with addresses nd limits. Based on e-manual http://support.robotis.com/en/product/dynamixel/xl-series/xl-320.htm
 *
 * Created by skokan on 25.4.15.
 */
public enum Data {

    MODEL_NUMBER(0, 2, true, 350, null, null),
    FIRMWARE_VERSION(2, 1, true, null, null, null),
    ID(3, 1, false, 1, 0, 252),
    BAUD_RATE(4, 1, false, 3, 0, 3),
    DELAY_TIME(5, 1, false, 250, 0, 254),
    CW_ANGLE_LIMIT(6, 2, false, 0, 0, 1023),
    CCW_ANGLE_LIMIT(8, 2, false, 1023, 0, 1023),
    CONTROL_MDE(11, 1, false, 2, 1, 2),
    LIMIT_TEMPERATURE(12, 1, false, 65, 0, 150),
    LOWER_LIMIT_VOLTAGE(13, 1, false, 60, 50, 250),
    UPPER_LIMIT_VOLTAGE(14, 1, false, 90, 50, 250),
    MAX_TOURGE(15, 2, false, 1023, 0, 1023),
    RETURN_LEVEL(17, 1, false, 2, 0, 2),
    SHUTDOWN_ALARM(18, 1, false, 36, 0, 127),
    TORQUE_ENABLE(24, 1, false, 0, 0, 1),
    LED_ON_OFF(25, 1, false, 0, 0, 7),
    D_GAIN(27, 1, false, 0, 0, 254),
    I_GAIN(28, 1, false, 0, 0, 254),
    P_GAIN(29, 1, false, 32, 0, 1023),
    GOAL_POSITION(30, 2, false, null, 0, 2047),
    GOAL_VELOCITY(32, 2, false, null, 0, 1023),
    GOAL_TOURGE(35, 2, false, null, 0, null),
    CURRENT_POSITION(37, 2, true, null, null, null),
    PRESENT_SPEED(39, 2, true, null, null, null),
    PRESENT_LOAD(41, 2, true, null, null, null),
    PRESENT_VOLTAGE(45, 1, true, null, null,null),
    PRESENT_TEMPERATURE(46, 1, true, null, null, null),
    REGISTERED_INSTRUCTION(47, 1, true, 0, null, null),
    MOVING(49, 1, true, 0, null, null),
    HARDWARE_ERROR_STATUS(50, 1, true, 0, null, null),
    PUNCH(51,2,false,32,0,1023);

    private final int address;
    private final int size;
    private final boolean readonly;
    private final Integer initValue;
    private final Integer min;
    private final Integer max;

    Data(int address, int size, boolean readonly, Integer initValue, Integer min, Integer max) {
        this.address = address;
        this.size = size;
        this.readonly = readonly;
        this.initValue = initValue;
        this.min = min;
        this.max = max;
    }
}
