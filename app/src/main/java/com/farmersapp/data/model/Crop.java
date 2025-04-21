package com.farmersapp.data.model;

public class Crop {
    private int id;
    private String name;
    private String field;
    private String plantingDate;
    
    public Crop(int id, String name, String field, String plantingDate) {
        this.id = id;
        this.name = name;
        this.field = field;
        this.plantingDate = plantingDate;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getField() {
        return field;
    }
    
    public String getPlantingDate() {
        return plantingDate;
    }
}
