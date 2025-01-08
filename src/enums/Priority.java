package enums;

public enum Priority {
    LOW("Низкая"),
    MEDIUM("Средняя"),
    HIGH("Высокая");

    private final String value;
    Priority(String priority) {
        this.value = priority;
    }

    public String getValue() {
        return value;
    }
}
