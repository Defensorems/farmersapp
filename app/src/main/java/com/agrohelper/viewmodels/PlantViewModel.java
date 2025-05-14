package com.agrohelper.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.agrohelper.database.AppDatabase;
import com.agrohelper.database.dao.PlantDao;
import com.agrohelper.database.dao.TaskDao;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlantViewModel extends AndroidViewModel {
    private final PlantDao plantDao;
    private final TaskDao taskDao;
    private final ExecutorService executorService;

    public PlantViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        plantDao = database.plantDao();
        taskDao = database.taskDao();
        executorService = Executors.newFixedThreadPool(4);
    }

    // Plant operations
    public LiveData<List<Plant>> getAllPlants() {
        return plantDao.getAllPlants();
    }

    public LiveData<Plant> getPlantById(int id) {
        return plantDao.getPlantById(id);
    }

    public LiveData<List<Plant>> getPlantsByType(String type) {
        return plantDao.getPlantsByType(type);
    }

    public void insertPlant(Plant plant) {
        executorService.execute(() -> {
            long plantId = plantDao.insert(plant);
        });
    }

    public void updatePlant(Plant plant) {
        executorService.execute(() -> plantDao.update(plant));
    }

    public void deletePlant(Plant plant) {
        executorService.execute(() -> plantDao.delete(plant));
    }

    // Task operations
    public LiveData<List<Task>> getTasksForPlant(int plantId) {
        return taskDao.getTasksForPlant(plantId);
    }

    public LiveData<List<Task>> getTasksInDateRange(Date startDate, Date endDate) {
        return taskDao.getTasksInDateRange(startDate, endDate);
    }

    public LiveData<List<Task>> getPendingTasks() {
        return taskDao.getPendingTasks();
    }

    public void insertTask(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void updateTask(Task task) {
        executorService.execute(() -> taskDao.update(task));
    }

    public void deleteTask(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
