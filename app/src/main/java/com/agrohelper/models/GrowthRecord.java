package com.agrohelper.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.agrohelper.database.DateConverter;

import java.util.Date;

@Entity(tableName = "growth_records",
        foreignKeys = @ForeignKey(entity = Plant.class,
                parentColumns = "id",
                childColumns = "plantId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("plantId")})
public class GrowthRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int plantId;

    @TypeConverters(DateConverter.class)
    private Date recordDate;

    private float height; // в сантиметрах
    private String stage; // например: "Всходы", "Цветение", "Плодоношение"
    private String notes;
    private String photoUri;

    public GrowthRecord(int plantId, Date recordDate, float height, String stage, String notes, String photoUri) {
        this.plantId = plantId;
        this.recordDate = recordDate;
        this.height = height;
        this.stage = stage;
        this.notes = notes;
        this.photoUri = photoUri;
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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
