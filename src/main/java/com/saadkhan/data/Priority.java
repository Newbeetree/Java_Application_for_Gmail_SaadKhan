package com.saadkhan.data;

/**
 * priorit of the email
 */
public enum Priority {
    PRIORITY_LOWEST(0),
    PRIORITY_LOW(1),
    PRIORITY_NORMAL(2),
    PRIORITY_HIGH(3),
    PRIORITY_HIGHEST(4);

    private int value;

    /**
     * Constructor of Enums which can take an ordinal value to set
     *
     * @param value ordinal value
     */
    Priority(int value) {
        this.value = value;
    }

    /**
     * @return ordinal value of enum
     */
    public int getValue() {
        return value;
    }
}
