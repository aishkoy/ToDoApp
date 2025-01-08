package model;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<Task>();
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }
}
