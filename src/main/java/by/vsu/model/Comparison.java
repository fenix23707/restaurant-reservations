package by.vsu.model;

public enum Comparison {
    GT(">"), GTE(">="), EQ("="), LTE("<="), LT("<");

    private final String value;

    Comparison(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
