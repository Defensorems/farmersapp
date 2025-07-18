package com.agrohelper.database;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.agrohelper.database.dao.GrowthRecordDao;
import com.agrohelper.database.dao.PlantDao;
import com.agrohelper.database.dao.TaskDao;
import com.agrohelper.models.GrowthRecord;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DatabaseExporter {
    private static final String TAG = "DatabaseExporter";
    private final AppDatabase database;
    private final Context context;
    private final Gson gson;

    public DatabaseExporter(Context context, AppDatabase database) {
        this.context = context;
        this.database = database;
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .serializeNulls()
                .create();
    }

    public File exportToJson() throws IOException, JSONException {
        // Получаем данные из всех таблиц
        PlantDao plantDao = database.plantDao();
        TaskDao taskDao = database.taskDao();
        GrowthRecordDao growthRecordDao = database.growthRecordDao();

        List<Plant> plants = plantDao.getAllPlantsSync();
        List<Task> tasks = taskDao.getAllTasksSync();
        List<GrowthRecord> growthRecords = growthRecordDao.getAllGrowthRecordsSync();

        // Создаем JSON структуру
        JSONObject databaseJson = new JSONObject();
        databaseJson.put("export_timestamp", System.currentTimeMillis());
        databaseJson.put("last_change_timestamp", AppDatabase.getLastChangeTimestamp());
        databaseJson.put("plants", new JSONArray(gson.toJson(plants)));
        databaseJson.put("tasks", new JSONArray(gson.toJson(tasks)));
        databaseJson.put("growth_records", new JSONArray(gson.toJson(growthRecords)));

        // Директория для экспорта
        File exportDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "exports");
        if (!exportDir.exists() && !exportDir.mkdirs()) {
            throw new IOException("Failed to create export directory");
        }

        // Форматируем имя файла с датой
        String fileName = String.format("agrohelper_export_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS.json",
                new Date(AppDatabase.getLastChangeTimestamp()));

        File exportFile = new File(exportDir, fileName);

        // Запись в файл
        try (FileWriter writer = new FileWriter(exportFile)) {
            writer.write(databaseJson.toString(4)); // Pretty print
            Log.i(TAG, "Database exported to: " + exportFile.getAbsolutePath());
        }

        return exportFile;
    }

    public interface ExportCallback {
        void onSuccess(File jsonFile);
        void onError(Exception e);
    }

    public static void exportInBackground(Context context, ExportCallback callback) {
        new Thread(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(context);
                DatabaseExporter exporter = new DatabaseExporter(context, db);
                File jsonFile = exporter.exportToJson();
                callback.onSuccess(jsonFile);
            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }
}