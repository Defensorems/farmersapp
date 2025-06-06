package com.agrohelper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.agrohelper.R;
import com.agrohelper.database.DateConverter;

import java.util.Date;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(entity = Plant.class,
                parentColumns = "id",
                childColumns = "plantId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("plantId")})
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int plantId;
    private TaskType taskType;

    @TypeConverters(DateConverter.class)
    private Date date;

    private boolean isDone;

    public enum TaskType {
        WATER(R.string.task_type_water),
        FERTILIZE(R.string.task_type_fertilize),
        HARVEST(R.string.task_type_harvest);

        private final int labelResId;

        TaskType(int labelResId) {
            this.labelResId = labelResId;
        }

        public int getLabelResId() {
            return labelResId;
        }
    }

    public Task(int plantId, TaskType taskType, Date date, boolean isDone) {
        this.plantId = plantId;
        this.taskType = taskType;
        this.date = date;
        this.isDone = isDone;
    }

    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
