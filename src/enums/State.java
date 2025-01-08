package enums;

import model.Task;

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
        public void changeTaskDescription(Task task) {

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
        public void changeTaskDescription(Task task) {

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
    public abstract void changeTaskDescription(Task task);
}
