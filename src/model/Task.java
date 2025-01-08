package model;

import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private LocalDate completionDate;
    private LocalDate creationDate;
    private String priority;

    public Task(String name, String description, LocalDate completionDate, LocalDate creationDate, String priority) {
        this.name = name;
        this.description = description;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("""
                ╔═════════════════════════════════════════════════════════════════
                ║ Name: %s
                ║ Description: %s
                ║ Creation Date: %s
                ║ Completion Date: %s
                ║ Priority: %s
                ╚═════════════════════════════════════════════════════════════════
                """, name, description, completionDate, creationDate, priority);
    }
}
