package com.agrohelper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.agrohelper.models.Task;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks WHERE plantId = :plantId ORDER BY date ASC")
    LiveData<List<Task>> getTasksForPlant(int plantId);

    @Query("SELECT * FROM tasks WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    LiveData<List<Task>> getTasksInDateRange(Date startDate, Date endDate);

    @Query("SELECT * FROM tasks WHERE isDone = 0 ORDER BY date ASC")
    LiveData<List<Task>> getPendingTasks();

    @Query("SELECT * FROM tasks WHERE taskType = :taskType ORDER BY date ASC")
    LiveData<List<Task>> getTasksByType(Task.TaskType taskType);
}
