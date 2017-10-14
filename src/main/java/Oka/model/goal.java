package Oka.model;

public abstract class goal {
    private final int value;

    public goal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
