package com.agrohelper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.agrohelper.models.GrowthRecord;

import java.util.List;

/**
 * Data Access Object для работы с записями о росте растений
 */
@Dao
public interface GrowthRecordDao {
    /**
     * Вставляет новую запись о росте растения в базу данных
     * @param growthRecord запись о росте растения
     * @return ID вставленной записи
     */
    @Insert
    long insert(GrowthRecord growthRecord);

    /**
     * Обновляет существующую запись о росте растения
     * @param growthRecord запись о росте растения
     */
    @Update
    void update(GrowthRecord growthRecord);

    /**
     * Удаляет запись о росте растения из базы данных
     * @param growthRecord запись о росте растения
     */
    @Delete
    void delete(GrowthRecord growthRecord);

    /**
     * Получает все записи о росте для конкретного растения
     * @param plantId ID растения
     * @return список записей о росте
     */
    @Query("SELECT * FROM growth_records WHERE plantId = :plantId ORDER BY recordDate DESC")
    LiveData<List<GrowthRecord>> getGrowthRecordsForPlant(int plantId);

    /**
     * Получает запись о росте по ID
     * @param id ID записи о росте
     * @return запись о росте
     */
    @Query("SELECT * FROM growth_records WHERE id = :id")
    LiveData<GrowthRecord> getGrowthRecordById(int id);

    /**
     * Получает все записи о росте
     * @return список всех записей о росте
     */
    @Query("SELECT * FROM growth_records ORDER BY recordDate DESC")
    LiveData<List<GrowthRecord>> getAllGrowthRecords();
}