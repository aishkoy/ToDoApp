package models;

import enums.Priority;
import enums.State;

import java.time.LocalDate;

public class Task {
    private final String name;
    private String description;
    private final Priority priority;
    private final LocalDate creationDate;
    private final LocalDate completionDate;
    private String rating;
    private State state;

    public Task(String name, String description, Priority priority, LocalDate creationDate, LocalDate completionDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.priority = priority;
        this.state = State.NEW;
        this.rating = "Не выставлена";
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

    public LocalDate getCompletionDate(){
        return completionDate;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getRating() {return rating;}

    public void setRating(String rating) {this.rating = rating;}

    @Override
    public String toString() {
        boolean isOverdue = completionDate.isBefore(LocalDate.now());
        String overdue = isOverdue ? "*просроченная задача\n" : "";
        return String.format("""
                %s╔═════════════════════════════════════════════════════════════════
                ║ Имя: %s
                ║ Описание: %s
                ║ Дата создания: %s
                ║ Дедлайн: %s
                ║ Приоритет: %s
                ║ Статус: %s
                ║ Оценка: %s
                ╚═════════════════════════════════════════════════════════════════
                """,overdue, name, description, creationDate, completionDate, priority.getValue(), state.getValue(), rating);
    }
}
