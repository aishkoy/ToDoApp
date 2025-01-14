package models;

import enums.Priority;
import enums.State;
import exceptions.EmptyTaskListException;
import services.IOManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        if (tasks == null) {
            this.tasks = new ArrayList<>();
            return;
        }
        this.tasks.addAll(tasks);
    }

    public void showAllTasks() {
        printTasks(tasks, "");
    }

    public void createTask() {
        String name = IOManager.getValidInput(".*", "Введите имя задачи: ");
        String description = getUserDescription();
        Priority priority = choicePriority();
        LocalDate[] dates = getDatesFromUser("Введите дату создания: ", "Введите дедлайн: ");
        tasks.add(new Task(name, description, priority, dates[0], dates[1]));
    }

    public void changeTaskDescription(Task task) {
        try {
            task.getState().changeTaskDescription(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeTaskState(Task task) {
        try {
            task.getState().changeState(task, choiceState(task));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTask(Task task) {
        try {
            task.getState().deleteTak(task, this.tasks);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rateTask(Task task) {
        try {
            task.getState().rateTask(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sortByPriority(boolean isAscending) {
        tasks.stream()
                .sorted(isAscending ? Comparator.comparing(Task::getPriority) : Comparator.comparing(Task::getPriority).reversed())
                .forEach(System.out::println);
    }

    public void sortByCreationDate(boolean isAscending) {
        tasks.stream()
                .sorted(isAscending ? Comparator.comparing(Task::getCreationDate) : Comparator.comparing(Task::getCreationDate).reversed())
                .forEach(System.out::println);
    }

    public void sortByName(boolean isAscending) {
        tasks.stream()
                .sorted(isAscending ? Comparator.comparing(Task::getName) : Comparator.comparing(Task::getName).reversed())
                .forEach(System.out::println);
    }

    public void sortByDescription(boolean isAscending) {
        tasks.stream()
                .sorted(isAscending ? Comparator.comparing(Task::getDescription) : Comparator.comparing(Task::getDescription).reversed())
                .forEach(System.out::println);
    }

    public void searchByKeyword(String word) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getName().toLowerCase().contains(word.toLowerCase()))
                .toList();

        printTasks(filteredTasks, "Нет задач с таким ключевым словом в названии!");
    }

    public void searchByDate() {
        LocalDate[] dates = getDatesFromUser("Введите начальную дату: ", "Введите конечную дату: ");

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getCompletionDate().isAfter(dates[0]) && task.getCompletionDate().isBefore(dates[1]))
                .toList();

        printTasks(filteredTasks, "Нет задач в указанном временном диапазоне!");
    }

    public void filterByPriority() {
        Priority priority = choicePriority();

        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .toList();

        printTasks(filteredTasks, "Нет задач с таким приоритетом!");
    }

    public void searchByPriority() {
        String priorityStr = IOManager.getValidInput(".*", "Введите приоритет: ");
        for(Priority priority : Priority.values()) {
            if(priority.getValue().equalsIgnoreCase(priorityStr)) {
                List<Task> filteredTasks =  tasks.stream()
                        .filter(task -> task.getPriority() == priority)
                        .toList();
                printTasks(filteredTasks, "Нет задач с таким приоритетом!");
                return;
            }
        }
        System.out.println("Не существует такого приоритета задач!");
    }

    public void filterByName(String name){
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getName().equalsIgnoreCase(name.toLowerCase()))
                .toList();

        printTasks(filteredTasks, "Нет задач с таким именем!");
    }

    public void filterByCreationDate() {
        LocalDate creationDateSearch = getValidDate("Введите дату создания: ");
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getCreationDate() == creationDateSearch)
                .toList();
        printTasks(filteredTasks, "Нет задач, созданных в данную дату");
    }

    private String getUserDescription() {
        System.out.println("""
                
                Вы можете ввести описание или оставить его пустым на данный момент
                1. Ввести описание
                2. Пропустить этот шаг
                """);
        String choice = IOManager.getValidInput("^[1-2]$", "Введите число: ");

        return choice.equals("1")
                ? IOManager.getValidInput(".*", "Введите описание задачи: ")
                : " ";
    }

    private static Priority choicePriority() {
        System.out.println("Выберите приоритет задачи: ");
        for (Priority priority : Priority.values()) {
            System.out.println(priority.ordinal() + 1 + ". " + priority.getValue());
        }
        int choice = Integer.parseInt(IOManager.getValidInput("^[1-3]$", "Введите число: "));
        return Priority.values()[choice - 1];
    }

    private LocalDate[] getDatesFromUser(String start, String end) {
        LocalDate[] dates = new LocalDate[2];
        do {
            dates[0] = getValidDate(start);
            dates[1] = getValidDate(end);
        } while (!isDatesValid(dates[0], dates[1]));

        return dates;
    }

    private boolean isDatesValid(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            System.out.println("\nНачальная дата не может быть позже конечной даты! Попробуйте снова!");
            return false;
        }
        return true;
    }

    private LocalDate getValidDate(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return LocalDate.parse(IOManager.getValidInput("^([0-2]\\d|3[01])\\.(0\\d|1[0-2])\\.\\d{4}$", message), formatter);
        } catch (Exception e) {
            System.out.println("Неверный ввод даты! Введите дату в формате 'dd.mm.yyyy'");
            return getValidDate(message);
        }
    }

    private State choiceState(Task task) {
        int exceptionInt = 0;
        System.out.println("\nКакой статус вы хотите назначить задаче?");
        for (State state : State.values()) {
            if (!task.getState().equals(state)) {
                System.out.println(state.ordinal() + 1 + ". " + state.getValue());
                continue;
            }
            exceptionInt = task.getState().ordinal() + 1;
        }

        int choice = Integer.parseInt(IOManager.getValidInput("^[1-3]$", "Введите число: "));

        if (exceptionInt == choice) {
            System.out.println("Неверный ввод! Введите только указанные цифры!");
            return choiceState(task);
        }

        return State.values()[choice - 1];
    }

    private void printTasks(List<Task> tasks, String message) {
        if (tasks.isEmpty()) {
            throw new EmptyTaskListException(message);
        } else {
            System.out.println("\n═══════════════════════════════ СПИСОК ЗАДАЧ ═══════════════════════════════");
            tasks.forEach(System.out::println);
        }
    }
}
