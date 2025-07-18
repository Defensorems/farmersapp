package com.agrohelper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.agrohelper.models.Plant;

import java.util.List;

@Dao
public interface PlantDao {
    @Insert
    long insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plants ORDER BY name ASC")
    LiveData<List<Plant>> getAllPlants();

    // Синхронная версия для экспорта
    @Query("SELECT * FROM plants")
    List<Plant> getAllPlantsSync();

    @Query("SELECT * FROM plants WHERE id = :id")
    LiveData<Plant> getPlantById(int id);

    @Query("SELECT * FROM plants WHERE type = :type")
    LiveData<List<Plant>> getPlantsByType(String type);
}