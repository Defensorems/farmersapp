package com.farmersapp.ui.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.farmersapp.data.model.Task;
import com.farmersapp.data.repository.TaskRepository;

import java.util.List;

public class TasksViewModel extends ViewModel {

    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private TaskRepository taskRepository;

    public TasksViewModel() {
        // Инициализация без DI для совместимости с существующим кодом
        taskRepository = new TaskRepository();
        loadTasks();
    }

    public void loadTasks() {
        isLoading.setValue(true);
        try {
            List<Task> taskList = taskRepository.getAllTasks();
            tasks.setValue(taskList);
        } catch (Exception e) {
            errorMessage.setValue("Failed to load tasks: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    public void addTask(Task task) {
        isLoading.setValue(true);
        try {
            taskRepository.addTask(task);
            loadTasks(); // Обновить список
        } catch (Exception e) {
            errorMessage.setValue("Failed to add task: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    public void updateTask(Task task) {
        isLoading.setValue(true);
        try {
            taskRepository.updateTask(task);
            loadTasks(); // Обновить список
        } catch (Exception e) {
            errorMessage.setValue("Failed to update task: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    public void deleteTask(int taskId) {
        isLoading.setValue(true);
        try {
            taskRepository.deleteTask(taskId);
            loadTasks(); // Обновить список
        } catch (Exception e) {
            errorMessage.setValue("Failed to delete task: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    // Геттеры для LiveData
    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}