package models;

import exceptions.EmptyTaskListException;
import services.IOManager;
import services.JsonHandler;

import java.util.List;

public class UserIO {
    private static final TaskManager tm = new TaskManager();

    public UserIO() {
        JsonHandler.readJson("tasks.json");
        tm.setTasks(JsonHandler.getTasks());
    }

    public void run() {
        System.out.println("\nДобро пожаловать в приложения для управления задачами!");
        while (true) {
            showActionMenu();
            processMenuChoice();
            JsonHandler.writeJson(tm.getTasks());
        }
    }


    private void showActionMenu() {
        System.out.println("""
                
                        МЕНЮ ДЕЙСТВИЙ:
                1.  - Отобразить все задачи
                2.  - Добавить новую задачу
                3.  - Изменить описание задачи
                4.  - Изменить статус задачи
                5.  - Удалить задачу
                6.  - Оценить задачу
                
                7.  - Отсортировать по названию
                8.  - Отсортировать по приоритету
                9.  - Отсортировать по дате создания
                10. - Отсортировать по описанию
                
                11. - Отфильтровать по ключевому слову в имени задачи
                12. - Отфильтровать задачи по временному диапазону
                13. - Отфильтровать по приоритету
                
                0 - Выйти
                """);
    }

    private void processMenuChoice() {
        String choice = IOManager.getValidInput("^(1[0-3]|[0-9])$", "Введите число: ");
        try {
            switch (choice) {
                case "1" -> tm.showAllTasks();
                case "2" -> tm.createTask();
                case "3", "4", "5", "6" -> processWithTask(choice);
                case "7", "8", "9", "10" -> sortTasks(choice);
                case "11", "12", "13" -> filterTasks(choice);
                case "0" -> {
                    System.out.println("\nДо свиданья!");
                    System.exit(0);
                }
            }
        } catch (EmptyTaskListException e) {
            System.out.println("Список задач пуст! Создайте задачу!");
            System.out.println(e.getMessage());
        }
    }

    private void processWithTask(String number) {
        Task task = getTask();
        if (task == null) {
            throw new EmptyTaskListException();
        }
        switch (number) {
            case "3" -> tm.changeTaskDescription(task);
            case "4" -> tm.changeTaskState(task);
            case "5" -> tm.deleteTask(task);
            case "6" -> tm.rateTask(task);
        }
    }

    private void sortTasks(String number) {
        if (tm.getTasks().isEmpty()) {
            throw new EmptyTaskListException();
        }

        switch (number) {
            case "7" -> tm.sortByName(askSortOrder());
            case "8" -> tm.sortByPriority(askSortOrder());
            case "9" -> tm.sortByCreationDate(askSortOrder());
            case "10" -> tm.sortByDescription(askSortOrder());
        }
    }

    private void filterTasks(String number) {
        if (tm.getTasks().isEmpty()) {
            throw new EmptyTaskListException();
        }

        switch (number) {
            case "11" -> tm.filterByKeyword(IOManager.getValidInput(".*", "Введите ключевое слово: "));
            case "12" -> tm.filterByDate();
            case "13" -> tm.filterByPriority();
        }
    }

    private static Task getTask() {
        List<Task> tasks = tm.getTasks();

        if (tasks.isEmpty()) {
            return null;
        }

        System.out.println("\nСПИСОК ЗАДАЧ: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("#" + (i + 1));
            System.out.println(tasks.get(i));
        }

        int taskIndex;
        do {
            taskIndex = Integer.parseInt(IOManager.getValidInput("[1-9]\\d*", "Введите номер задачи: "));
            if (taskIndex < 1 || taskIndex > tasks.size()) {
                System.out.println("Неверный ввод!");
            }
        } while (taskIndex < 1 || taskIndex > tasks.size());

        return tasks.get(taskIndex - 1);
    }

    private boolean askSortOrder() {
        System.out.println("""
                
                        ВЫБЕРИТЕ ВАРИАНТ СОРТИРОВКИ
                1. - По возрастанию
                2. - По убыванию
                """);
        return IOManager.getValidInput("^[1-2]$", "Введите число: ").equals("1");
    }
}
