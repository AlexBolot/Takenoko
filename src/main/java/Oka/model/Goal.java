package Oka.model;

public abstract class Goal {
    private final int value;

    public Goal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
