package com.agrohelper.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;
import com.agrohelper.models.GrowthRecord;
import com.agrohelper.database.dao.PlantDao;
import com.agrohelper.database.dao.TaskDao;
import com.agrohelper.database.dao.GrowthRecordDao;

@Database(entities = {Plant.class, Task.class, GrowthRecord.class}, version = 4, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "agrohelper_db";
    private static volatile AppDatabase instance;

    public abstract PlantDao plantDao();
    public abstract TaskDao taskDao();
    public abstract GrowthRecordDao growthRecordDao();

    // Метод для получения экземпляра БД
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    // Метод для закрытия БД (вызывайте его при завершении работы с БД)
    public static void destroyInstance() {
        if (instance != null && instance.isOpen()) {
            instance.close();
            instance = null;
        }
    }
}