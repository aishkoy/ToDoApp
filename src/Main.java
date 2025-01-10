import model.Task;
import model.TaskManager;
import services.IOManager;
import services.JsonHandler;

import java.util.List;

public class Main {
    private final static TaskManager tm = new TaskManager();

    public static void main(String[] args) {
        System.out.println("Добро пожаловать!\n");
        JsonHandler.readJson(IOManager.getValidInput("[a-zA-Z_.]+", "Введите имя файла (пример: something.json): "));
        tm.setTasks(JsonHandler.getTasks());
        run();
        System.out.println("До свиданья!");
    }

    private static void run() {
        AppExit:
        while (true) {
            System.out.println("""
                    
                    МЕНЮ ДЕЙСТВИЙ:
                    1. - Отобразить все задачи
                    2. - Добавить новую задачу
                    3. - Изменить описание задачи
                    4. - Изменить статус задачи
                    5. - Удалить задачу
                    6. - Оценить задачу
                    
                    7. - Отсортировать по названию
                    8. - Отсортировать по приоритету
                    9. - Отсортировать по дате создания
                    10. - Отсортировать по описанию
                    
                    11. - Отфильтровать по ключевому слову в имени задачи
                    12. - Отфильтровать по месяцу
                    13. - Отфильтровать по приоритету
                    
                    0 - Выйти
                    """);
            String choice = IOManager.getValidInput("^(1[0-3]|[0-9])$", "Введите число: ");
            switch (choice) {
                case "1" -> tm.showAllTasks();
                case "2" -> tm.createTask();
                case "3" -> tm.changeTaskDescription(getTask());
                case "4" -> tm.changeTaskState(getTask());
                case "5" -> tm.deleteTask(getTask());
                case "6" -> tm.rateTask(getTask());
                case "7" -> tm.sortByName(askSortOrder());
                case "8" -> tm.sortByPriority(askSortOrder());
                case "9" -> tm.sortByCreationDate(askSortOrder());
                case "10" -> tm.sortByDescription(askSortOrder());
                case "11" -> tm.filterByKeyword(IOManager.getValidInput(".*", "Введите ключевое слово: "));
                case "12" -> tm.filterByDate();
                case "13" -> tm.filterByPriority();
                case "0" -> {
                    break AppExit;
                }
            }
            JsonHandler.writeFile(tm.getTasks());
        }
    }

    private static boolean askSortOrder(){
        System.out.println("""
                
                Выберите порядок сортировки?
                1. - По возрастанию
                2. - По убыванию
                """);
        return IOManager.getValidInput("^[1-2]$", "Введите число: ").equals("1");
    }

    private static Task getTask(){
        List<Task> tasks = tm.getTasks();

        if(tasks.isEmpty()){
            System.out.println("Список задач пуст. Создайте задачу!");
            return null;
        }

        System.out.println("Список задач: ");
        for(int i = 0 ; i < tasks.size() ; i++){
            System.out.println(i+1 + ". " + tasks.get(i).getName());
        }

        int taskIndex;
        do{
            taskIndex = Integer.parseInt(IOManager.getValidInput("[1-9]\\d*", "Введите номер задачи: "));
            if(taskIndex < 1 || taskIndex > tasks.size()){
                System.out.println("Неверный ввод! ");
            }
        } while(taskIndex < 1 || taskIndex > tasks.size());

        return tasks.get(taskIndex-1);
    }
}