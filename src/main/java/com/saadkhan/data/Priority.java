package com.saadkhan.data;

public enum Priority {
    PRIORITY_LOWEST (0),
    PRIORITY_LOW (1),
    PRIORITY_NORMAL (2),
    PRIORITY_HIGH (3),
    PRIORITY_HIGHEST (4);

    Priority(int value){
        this.value = value;
    }

    private int value;

    public int getValue(){
        return value;
    }
}
