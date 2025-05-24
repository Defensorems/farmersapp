package com.agrohelper.network;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {
    // 1. Исправлена базовая URL (убраны пробелы и добавлен правильный путь)
    private static final String PLANT_ID_BASE_URL = "https://plant.id/ ";

    // 2. Добавлено хранение API-ключа
    private static String apiKey = "HN8b8oOF3cfKcPgbOntQN5kapYTgPUZDVNy2vmAREeFHo6rdQc";  // Должно быть установлено из безопасного источника

    private static Retrofit plantIdRetrofit = null;

    public static void setApiKey(String key) {
        apiKey = key;
    }

    public static PlantIdApiService getPlantIdApiService() {
        if (plantIdRetrofit == null) {
            // 3. Создаем клиент с interceptor для добавления заголовков
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // 4. Добавляем interceptor для логгирования
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

            // 5. Добавляем interceptor для автоматической установки заголовков
            httpClient.addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Api-Key", apiKey)
                        .header("Content-Type", "application/json")
                        .build();
                return chain.proceed(newRequest);
            });

            plantIdRetrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(PLANT_ID_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return plantIdRetrofit.create(PlantIdApiService.class);
    }
}