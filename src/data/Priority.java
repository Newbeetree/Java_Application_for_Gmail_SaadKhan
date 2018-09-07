package data;

public enum Priority {
    PRIORITY_LOW (0),
    PRIORITY_LOWEST (1),
    PRIORITY_NORMAL (2),
    PRIORITY_HIGHEST (3),
    PRIORITY_HIGH (4);

    Priority(int value){
        this.value = value;
    }
    private int value;
    public int getValue(){
        return value;
    }
}
