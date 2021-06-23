package utils;

public enum MenuConstValue {
    defaultValue(0),
    newGame(1),
    credits(2),
    quit(3),
    error(4);

    private final int value;

    private MenuConstValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
