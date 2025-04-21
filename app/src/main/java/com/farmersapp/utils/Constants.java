package com.farmersapp.utils;

public class Constants {

    // Общие константы
    public static final String APP_NAME = "FarmersApp";
    public static final String APP_VERSION = "1.0.0";

    // Константы для API
    public static final String WEATHER_API_BASE_URL = "https://api.weatherapi.com/v1/";
    public static final String WEATHER_API_KEY = "YOUR_API_KEY"; // Заменить на реальный ключ

    // Константы для базы данных
    public static final String DATABASE_NAME = "farmers_app_database";
    public static final int DATABASE_VERSION = 1;

    // Константы для SharedPreferences
    public static final String PREFS_NAME = "farmers_app_prefs";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_FARM_ID = "farm_id";
    public static final String PREF_FARM_NAME = "farm_name";
    public static final String PREF_LAST_SYNC = "last_sync";

    // Константы для Intent
    public static final String EXTRA_CROP_ID = "crop_id";
    public static final String EXTRA_FIELD_ID = "field_id";
    public static final String EXTRA_TASK_ID = "task_id";

    // Константы для уведомлений
    public static final String NOTIFICATION_CHANNEL_ID = "farmers_app_channel";
    public static final String NOTIFICATION_CHANNEL_NAME = "FarmersApp Notifications";
    public static final int NOTIFICATION_ID_TASK = 1001;
    public static final int NOTIFICATION_ID_WEATHER = 1002;

    private Constants() {
        // Приватный конструктор для предотвращения создания экземпляров
    }
}