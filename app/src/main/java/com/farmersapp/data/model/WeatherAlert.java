package com.farmersapp.data.model;

import java.util.Date;

public class WeatherAlert {
    private int id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private AlertSeverity severity;

    public WeatherAlert() {
        // Пустой конструктор для Room
    }

    public WeatherAlert(int id, String title, String description, Date startTime, Date endTime, AlertSeverity severity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.severity = severity;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }

    public enum AlertSeverity {
        ADVISORY, WARNING, WATCH, EMERGENCY
    }
}