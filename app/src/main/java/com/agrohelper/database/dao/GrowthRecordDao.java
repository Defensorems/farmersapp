package com.agrohelper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.agrohelper.models.GrowthRecord;

import java.util.List;

@Dao
public interface GrowthRecordDao {
    @Insert
    long insert(GrowthRecord growthRecord);

    @Update
    void update(GrowthRecord growthRecord);

    @Delete
    void delete(GrowthRecord growthRecord);

    @Query("SELECT * FROM growth_records WHERE plantId = :plantId ORDER BY recordDate DESC")
    LiveData<List<GrowthRecord>> getGrowthRecordsForPlant(int plantId);

    // Синхронная версия для экспорта
    @Query("SELECT * FROM growth_records")
    List<GrowthRecord> getAllGrowthRecordsSync();

    @Query("SELECT * FROM growth_records WHERE id = :id")
    LiveData<GrowthRecord> getGrowthRecordById(int id);
}