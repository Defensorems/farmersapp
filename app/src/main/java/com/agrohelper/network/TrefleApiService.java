package com.agrohelper.network;

import com.agrohelper.models.PlantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TrefleApiService {
    @GET("api/v1/plants/search")
    Call<PlantResponse> searchPlants(
            @Header("Authorization") String apiKey, // Токен в заголовке
            @Query("q") String query
    );
}