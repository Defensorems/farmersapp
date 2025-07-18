package com.agrohelper.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.agrohelper.database.dao.GrowthRecordDao;
import com.agrohelper.database.dao.PlantDao;
import com.agrohelper.database.dao.TaskDao;
import com.agrohelper.models.GrowthRecord;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;

import java.io.File;
import java.util.Date;

@Database(entities = {Plant.class, Task.class, GrowthRecord.class}, version = 4, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DATABASE_NAME = "agrohelper_db";
    private static volatile AppDatabase instance;

    // Флаг для включения/выключения автоэкспорта
    private static volatile boolean autoExportEnabled = true;

    // Время последнего изменения
    private static volatile long lastChangeTimestamp = 0;

    // Задержка перед экспортом (мс)
    private static final int EXPORT_DELAY = 5000;
    private static final Handler exportHandler = new Handler(Looper.getMainLooper());

    public abstract PlantDao plantDao();
    public abstract TaskDao taskDao();
    public abstract GrowthRecordDao growthRecordDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    lastChangeTimestamp = System.currentTimeMillis();
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }

    public static void destroyInstance() {
        if (instance != null) {
            if (instance.isOpen()) instance.close();
            instance = null;
        }
    }

    // Метод для инициирования экспорта
    public static void exportToJson(Context context, DatabaseExporter.ExportCallback callback) {
        DatabaseExporter.exportInBackground(context, callback);
    }

    // Включение/выключение автоэкспорта
    public static void setAutoExportEnabled(boolean enabled) {
        autoExportEnabled = enabled;
        Log.i(TAG, "Auto-export " + (enabled ? "enabled" : "disabled"));
    }

    // Проверка статуса автоэкспорта
    public static boolean isAutoExportEnabled() {
        return autoExportEnabled;
    }

    // Получение времени последнего изменения
    public static long getLastChangeTimestamp() {
        return lastChangeTimestamp;
    }

    // Метод для регистрации изменений
    public static void notifyDataChanged(Context context) {
        lastChangeTimestamp = System.currentTimeMillis();
        Log.i(TAG, "Database changed at " + new Date(lastChangeTimestamp));

        if (autoExportEnabled) {
            // Отменяем предыдущие отложенные экспорты
            exportHandler.removeCallbacksAndMessages(null);

            // Планируем новый экспорт с задержкой
            exportHandler.postDelayed(() -> {
                Log.i(TAG, "Auto-exporting database...");
                exportToJson(context, new DatabaseExporter.ExportCallback() {
                    @Override
                    public void onSuccess(File jsonFile) {
                        Log.i(TAG, "Auto-export successful: " + jsonFile.getName());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Auto-export failed: " + e.getMessage());
                    }
                });
            }, EXPORT_DELAY);
        }
    }
}