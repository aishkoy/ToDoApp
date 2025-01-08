package model;

import enums.Priority;
import services.IOManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<Task>();
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        System.out.println("Список всех задач: ");
        tasks.forEach(System.out::println);
    }

    public void createTask() {
        String name = IOManager.getValidInput("[\\s\\S]", "Введите имя задачи: ");
        String description = IOManager.getValidInput("[\\s\\S]", "Введите описание задачи: ");
        String priority = choicePriority();
        LocalDate creationDate = getValidDate("Введите дату создания задачи: ");
        LocalDate completionDate = getValidDate("Введите дедлайн задачи: ");

        tasks.add(new Task(name, description, priority, creationDate, completionDate));
    }

    private String choicePriority() {
        System.out.println("Выберите приоритет задачи: ");
        for (Priority priority : Priority.values()) {
            System.out.println(priority.ordinal() + 1 + ". " + priority.getValue());
        }
        int choice = Integer.parseInt(IOManager.getValidInput("^[1-3]$", "Введите число: "));
        return Priority.values()[choice - 1].getValue();
    }

    private LocalDate getValidDate(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            System.out.print(message);
            return LocalDate.parse(IOManager.getValidInput("^([0-2]\\d|3[01])\\.(0\\d|1[0-2])\\.\\d{4}$", ""), formatter);
        } catch (Exception e) {
            System.out.println("Неверный ввод даты! Введите дату в формате 'dd.mm.yyyy'");
            return getValidDate(message);
        }
    }
}
