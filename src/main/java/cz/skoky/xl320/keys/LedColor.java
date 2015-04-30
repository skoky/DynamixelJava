package cz.skoky.xl320.keys;

/**
 * Created by skokan on 29.4.15.
 */
public enum LedColor {
    OFF(0),
    WHITE(7),
    PINK(5),
    BLUE_GREEN(6),
    YELLOW(3),
    BLUE(4),
    GREEN(2),
    RED(1);

    private final int bit;

    LedColor(int bit) {
        this.bit=bit;
    }

    public int getNumber() {
        return bit;
    }
}
