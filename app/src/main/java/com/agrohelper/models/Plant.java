package com.agrohelper.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.agrohelper.database.DateConverter;

import java.util.Date;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private String type;
    
    @TypeConverters(DateConverter.class)
    private Date datePlanted;
    
    private String notes;
    private String photoUri;

    public Plant(String name, String type, Date datePlanted, String notes, String photoUri) {
        this.name = name;
        this.type = type;
        this.datePlanted = datePlanted;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(Date datePlanted) {
        this.datePlanted = datePlanted;
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
