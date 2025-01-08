package model;

import enums.State;

import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private String priority;
    private LocalDate creationDate;
    private LocalDate completionDate;

    private State state;

    public Task(String name, String description, String priority, LocalDate completionDate, LocalDate creationDate) {
        this.name = name;
        this.description = description;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.state = State.NEW;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
