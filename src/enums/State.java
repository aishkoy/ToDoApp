package enums;

import exceptions.StateException;
import model.Task;
import services.IOManager;

import java.util.List;

public enum State {
    NEW("Новая") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) {
            taskList.remove(task);
            System.out.println("Задача была успешно удалена!");
        }

        @Override
        public void changeState(Task task, State state) throws StateException {
            if(state == State.IN_PROGRESS) {
                task.setState(IN_PROGRESS);
                System.out.println("Статус задачи был успешно изменен на 'в процессе'.");
            } else if(state == State.DONE) {
                throw new StateException("Вы не можете сменить статус задачи на 'завершена'!");
            }
        }

        @Override
        public void changeTaskDescription(Task task) {
            System.out.println("Предыдущее описание задачи: " + task.getDescription());
            String newDescription = IOManager.getValidInput(".*", "Введите новое описание задачи: ");
            task.setDescription(newDescription);
            System.out.println("Описание задачи было успешно изменено!\n");
        }
    },
    IN_PROGRESS("В процессе") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) throws StateException {
            throw new StateException("Задача находится в процессе выполнения. Вы не можете ее удалить!");
        }

        @Override
        public void changeState(Task task, State state) throws StateException {
            if (state == State.DONE) {
                task.setState(DONE);
                System.out.println("Статус задачи был успешно изменен на 'завершена'.");
            } else if (state == State.NEW) {
                throw new StateException("Вы не можете изменить состояние задачи на 'новая'!");
            }
        }

        @Override
        public void changeTaskDescription(Task task) throws StateException {
            throw new StateException("Задача находится в процессе выполнения. Вы не можете изменить описание задачи!");
        }
    },
    DONE("Завершена") {
        @Override
        public void deleteTak(Task task, List<Task> taskList) throws StateException {
            throw new StateException("Задача уже выполнена. Вы не можете ее удалить!");
        }

        @Override
        public void changeState(Task task, State state) throws StateException {
            throw new StateException("Вы не можете изменить состояние задачи, так как она уже выполнена!");
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

    public abstract void deleteTak(Task task, List<Task> taskList) throws StateException;
    public abstract void changeState(Task task, State state) throws StateException;
    public abstract void changeTaskDescription(Task task) throws StateException;
}
