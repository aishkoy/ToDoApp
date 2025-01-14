package services;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Task;

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
    private static List<Task> tasks = new ArrayList<>();
    private static final Type TYPE = new TypeToken<List<Task>>() {}.getType();

    private static Path PATH;
    private static final String DIRECTORY = "src/data/";
    private static final Gson GSON = Converters.registerAll(new GsonBuilder())
            .setPrettyPrinting().serializeNulls().create();

    private JsonHandler(){}

    public static void readJson(String fileName){
        try {
            PATH = getPath(fileName);
            String json = Files.readString(PATH);
            tasks = GSON.fromJson(json, TYPE);
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            offerFileAction();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }
    }


    public static void writeJson(List<Task> tasks){
        try{
            String newJson = GSON.toJson(tasks, TYPE);
            Files.write(PATH, newJson.getBytes());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи файла: " + e.getMessage());
        }

    }

    private static Path getPath(String fileName) throws FileNotFoundException {
        File file = new File(DIRECTORY + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("Такого файла не существует!");
        }
        return file.toPath();
    }

    private static void offerFileAction(){
        System.out.println("""
                
                Выберите одно из следующих действий:
                1. - Ввести другое название файла
                2. - Создать файл""");
        String choice = IOManager.getValidInput("[1-2]", "Введите число: ");
        switch (choice){
            case "1" -> readJson(IOManager.getValidInput("[a-zA-Z0-9._-]+", "Введите имя файла (пример: something.json): "));
            case "2" -> createFile();
        }
    }

    private static void createFile(){
        String fileName = IOManager.getValidInput("[a-zA-Z0-9._-]+", "Введите имя файла (пример: something.json): ");
        String fullPath = DIRECTORY + fileName;
        try(FileWriter _ = new FileWriter(fullPath)){
            System.out.println("Пустой файл " + fileName + " был успешно создан!");
            PATH = Paths.get(fullPath);
        } catch (IOException e){
            System.out.println("Произошла ошибка при создании файла: " + e.getMessage());
        }
    }

    public static List<Task> getTasks() {
        return tasks;
    }
}
