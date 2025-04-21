package com.farmersapp.data.model;

public class CropStatistics {
    private int totalCrops;
    private double totalArea;
    private double estimatedYield;
    private double estimatedRevenue;
    private int healthyCrops;
    private int atRiskCrops;

    public CropStatistics() {
        // Пустой конструктор для Room
    }

    public CropStatistics(int totalCrops, double totalArea, double estimatedYield,
                          double estimatedRevenue, int healthyCrops, int atRiskCrops) {
        this.totalCrops = totalCrops;
        this.totalArea = totalArea;
        this.estimatedYield = estimatedYield;
        this.estimatedRevenue = estimatedRevenue;
        this.healthyCrops = healthyCrops;
        this.atRiskCrops = atRiskCrops;
    }

    // Геттеры и сеттеры
    public int getTotalCrops() {
        return totalCrops;
    }

    public void setTotalCrops(int totalCrops) {
        this.totalCrops = totalCrops;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getEstimatedYield() {
        return estimatedYield;
    }

    public void setEstimatedYield(double estimatedYield) {
        this.estimatedYield = estimatedYield;
    }

    public double getEstimatedRevenue() {
        return estimatedRevenue;
    }

    public void setEstimatedRevenue(double estimatedRevenue) {
        this.estimatedRevenue = estimatedRevenue;
    }

    public int getHealthyCrops() {
        return healthyCrops;
    }

    public void setHealthyCrops(int healthyCrops) {
        this.healthyCrops = healthyCrops;
    }

    public int getAtRiskCrops() {
        return atRiskCrops;
    }

    public void setAtRiskCrops(int atRiskCrops) {
        this.atRiskCrops = atRiskCrops;
    }
}