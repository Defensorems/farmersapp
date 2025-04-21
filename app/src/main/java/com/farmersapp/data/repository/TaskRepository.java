package com.farmersapp.data.repository;

import com.farmersapp.data.model.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepository {

    // TODO: Implement data sources (local database, remote API)

    public TaskRepository() {
        // Временная реализация без реальных источников данных
    }

    public List<Task> getAllTasks() {
        // Временная реализация с фиктивными данными
        return getDummyTasks();
    }

    public List<Task> getUpcomingTasks(int limit) {
        // Временная реализация с фиктивными данными
        List<Task> tasks = getDummyTasks();
        return tasks.subList(0, Math.min(limit, tasks.size()));
    }

    public void addTask(Task task) {
        // Временная реализация
        // В будущем будет добавление в базу данных и синхронизация с сервером
    }

    public void updateTask(Task task) {
        // Временная реализация
    }

    public void deleteTask(int taskId) {
        // Временная реализация
    }

    // Вспомогательный метод для создания фиктивных данных
    private List<Task> getDummyTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Полив пшеницы", "Полить поле 1", new Date(),
                Task.TaskPriority.HIGH, Task.TaskStatus.PENDING, 1, 1));
        tasks.add(new Task(2, "Внесение удобрений", "Внести удобрения на поле 2", new Date(),
                Task.TaskPriority.MEDIUM, Task.TaskStatus.PENDING, 2, 2));
        tasks.add(new Task(3, "Проверка на вредителей", "Проверить кукурузу на наличие вредителей", new Date(),
                Task.TaskPriority.LOW, Task.TaskStatus.COMPLETED, 2, 2));
        return tasks;
    }
}