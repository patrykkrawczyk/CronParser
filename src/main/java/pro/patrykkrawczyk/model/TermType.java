package pro.patrykkrawczyk.model;

import lombok.Getter;

@Getter
public enum TermType {

    MINUTE("minute", 0, 59),
    HOUR("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 0, 6);

    private final String name;
    private final int minValue;
    private final int maxValue;

    TermType(String name, int minValue, int maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return name;
    }

}

