package com.agrohelper.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlantResponse {
    @SerializedName("data")
    private List<TreflePlant> plants;

    public List<TreflePlant> getPlants() { return plants; }
}