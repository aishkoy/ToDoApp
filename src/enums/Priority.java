package enums;

public enum Priority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String value;
    Priority(String priority) {
        this.value = priority;
    }

    public String getValue() {
        return value;
    }
}
