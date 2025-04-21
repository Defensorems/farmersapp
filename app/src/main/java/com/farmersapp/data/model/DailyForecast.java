package com.farmersapp.data.model;

import java.util.Date;

public class DailyForecast {
    private Date date;
    private double minTemp;
    private double maxTemp;
    private int humidity;
    private double windSpeed;
    private String condition;
    private String iconCode;
    private double precipitationChance;

    public DailyForecast() {
        // Пустой конструктор для Room
    }

    public DailyForecast(Date date, double minTemp, double maxTemp, int humidity,
                         double windSpeed, String condition, String iconCode, double precipitationChance) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.iconCode = iconCode;
        this.precipitationChance = precipitationChance;
    }

    // Геттеры и сеттеры
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public double getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }
}