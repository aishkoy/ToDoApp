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

    public Task(String name, String description, Priority priority, LocalDate completionDate, LocalDate creationDate) {
        this.name = name;
        this.description = description;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
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
        return String.format("""
                ╔═════════════════════════════════════════════════════════════════
                ║ Имя: %s
                ║ Описание: %s
                ║ Дата создания: %s
                ║ Дедлайн: %s
                ║ Приоритет: %s
                ║ Статус: %s
                ║ Оценка: %s
                ╚═════════════════════════════════════════════════════════════════
                """, name, description, completionDate, creationDate, priority.getValue(), state.getValue(), rating);
    }
}
