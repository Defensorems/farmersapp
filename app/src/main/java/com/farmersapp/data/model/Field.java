package com.farmersapp.data.model;

import java.util.Date;
import java.util.List;

public class Field {
    private int id;
    private String name;
    private double area;
    private String soilType;
    private Date lastTilled;
    private List<GeoPoint> boundaries;

    public Field() {
        // Пустой конструктор для Room
    }

    public Field(int id, String name, double area, String soilType, Date lastTilled, List<GeoPoint> boundaries) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.soilType = soilType;
        this.lastTilled = lastTilled;
        this.boundaries = boundaries;
    }

    // Геттеры и сеттеры
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public Date getLastTilled() {
        return lastTilled;
    }

    public void setLastTilled(Date lastTilled) {
        this.lastTilled = lastTilled;
    }

    public List<GeoPoint> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<GeoPoint> boundaries) {
        this.boundaries = boundaries;
    }
}