package com.agrohelper.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;

public class NetworkClient {
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY); // Уровень логирования

    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // Таймаут соединения
            .readTimeout(30, TimeUnit.SECONDS)    // Таймаут чтения
            .writeTimeout(30, TimeUnit.SECONDS)   // Таймаут записи
            .build();
    }
}