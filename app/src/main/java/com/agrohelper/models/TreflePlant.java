package com.agrohelper.models;

import com.google.gson.annotations.SerializedName;

public class TreflePlant {
    @SerializedName("common_name")
    private String commonName;
    
    @SerializedName("scientific_name")
    private String scientificName;
    
    @SerializedName("image_url")
    private String imageUrl;

    // Геттеры
    public String getCommonName() { return commonName; }
    public String getScientificName() { return scientificName; }
    public String getImageUrl() { return imageUrl; }
}