package enums;

import exceptions.StateException;
import model.Task;
import services.IOManager;

import java.util.List;

public enum State {
    NEW("Новая") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) {

        }

        @Override
        public void changeState(Task task) {

        }

        @Override
        public void changeTaskDescription(Task task) {
            System.out.println("Предыдущее описание задачи: " + task.getDescription());
            String newDescription = IOManager.getValidInput("[\\s\\S]", "Введите новое описание задачи: ");
            task.setDescription(newDescription);
            System.out.println("Описание задачи было успешно изменено!\n");
        }
    },
    IN_PROGRESS("В процессе") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) {

        }

        @Override
        public void changeState(Task task) {

        }

        @Override
        public void changeTaskDescription(Task task) throws StateException {
            throw new StateException("Задача находится в процессе выполнения. Вы не можете изменить описание задачи!");
        }
    },
    DONE("Сделано") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) {

        }

        @Override
        public void changeState(Task task) {

        }

        @Override
        public void changeTaskDescription(Task task) throws StateException {
            throw new StateException("Задача уже выполнена. Вы не можете изменить ее описание!");
        }
    };

    private final String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract void deleteTak(Task task, List<Task> taskList);
    public abstract void changeState(Task task);
    public abstract void changeTaskDescription(Task task) throws StateException;
}
