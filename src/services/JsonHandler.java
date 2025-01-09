package services;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private List<Task> tasks = new ArrayList<>();
    private static final Type TASK_TYPE = new TypeToken<List<Task>>() {}.getType();

    private static Path PATH;
    private static final String DIRECTORY_PATH = "src/data/";
    private static final Gson GSON = Converters.registerAll(new GsonBuilder())
            .setPrettyPrinting().serializeNulls().create();

    private JsonHandler(String fileName) {
        PATH = Paths.get(DIRECTORY_PATH, fileName);
        try {
            String json = Files.readString(getPath(fileName));
            this.tasks = GSON.fromJson(json, TASK_TYPE);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }
    }

    public static void readJson(String json) {
        new JsonHandler(json);
    }

    public static void writeFile(List<Task> tasks) {
        try {
            String newJson = GSON.toJson(tasks, TASK_TYPE);
            Files.write(PATH, newJson.getBytes());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи файла: " + e.getMessage());
        }
    }

    public static Path getPath(String fileName) {
        try {
            File file = new File(DIRECTORY_PATH + fileName);
            if (!file.exists()) {
                throw new FileNotFoundException("Файл не найден!");
            }
            return file.toPath();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            promptFileAction();
            return PATH;
        }
    }

    private static void createFile() {
        String fileName = IOManager.getValidInput("[a-zA-Z_.]+", "Введите имя файла: ");
        String fullPath = DIRECTORY_PATH + File.separator + fileName;
        try (FileWriter _ = new FileWriter(fullPath)) {
            System.out.println("Пустой json файл с именем " + fileName + " был создан.");
            PATH = Paths.get(fullPath);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при создании файла: " + e.getMessage());;
        }
    }

    private static void promptFileAction() {
        System.out.println("""
                
                Выберите одно из следующих действий:
                1. - Ввести другое название файла
                2. - Создать файл""");
        String choice = IOManager.getValidInput("[1-2]", "Введите число: ");
        switch (choice) {
            case "1" -> readJson(IOManager.getValidInput("[a-zA-Z.]+", "Введите имя файла: "));
            case "2" -> createFile();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
