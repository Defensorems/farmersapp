package com.farmersapp.data.model;

import java.util.Date;

public class CropAlert {
    private int id;
    private String title;
    private String description;
    private Date date;
    private int cropId;
    private int fieldId;
    private AlertType type;

    public CropAlert() {
        // Пустой конструктор для Room
    }

    public CropAlert(int id, String title, String description, Date date, int cropId, int fieldId, AlertType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.cropId = cropId;
        this.fieldId = fieldId;
        this.type = type;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public enum AlertType {
        PEST, DISEASE, IRRIGATION, FERTILIZER, HARVEST
    }
}