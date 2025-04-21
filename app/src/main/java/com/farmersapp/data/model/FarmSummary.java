package com.farmersapp.data.model;

public class FarmSummary {
    private String farmName;
    private int totalFields;
    private int activeCrops;
    private int pendingTasks;
    
    public FarmSummary(String farmName, int totalFields, int activeCrops, int pendingTasks) {
        this.farmName = farmName;
        this.totalFields = totalFields;
        this.activeCrops = activeCrops;
        this.pendingTasks = pendingTasks;
    }
    
    public String getFarmName() {
        return farmName;
    }
    
    public int getTotalFields() {
        return totalFields;
    }
    
    public int getActiveCrops() {
        return activeCrops;
    }
    
    public int getPendingTasks() {
        return pendingTasks;
    }
}
