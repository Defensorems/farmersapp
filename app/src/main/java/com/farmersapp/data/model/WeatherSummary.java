package com.farmersapp.data.model;

public class WeatherSummary {
    private String location;
    private double temperature;
    private String condition;
    private String iconCode;

    public WeatherSummary() {
        // Пустой конструктор для Room
    }

    public WeatherSummary(String location, double temperature, String condition, String iconCode) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        this.iconCode = iconCode;
    }

    // Геттеры и сеттеры
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
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
}