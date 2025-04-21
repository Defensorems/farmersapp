package com.farmersapp.data.repository;

import com.farmersapp.data.model.Crop;
import java.util.ArrayList;
import java.util.List;

public class CropRepository {
    
    // TODO: Implement data sources (local database, remote API)
    
    public List<Crop> getCrops() {
        // Placeholder implementation
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop(1, "Wheat", "Field 1", "2023-04-15"));
        crops.add(new Crop(2, "Corn", "Field 2", "2023-05-01"));
        return crops;
    }
}
