package com.agrohelper.network;

import com.agrohelper.models.PlantIdResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PlantIdApiService {

    /**
     * Метод для поиска растений по текстовому запросу (использует q=... как в curl)
     */
    @GET("api/v3/kb/plants/name_search")
    Call<PlantIdResponse> searchPlants(@Query("q") String query);

    /**
     * Метод для идентификации растения по изображению
     */
    @Multipart
    @POST("api/v2/identify")
    Call<PlantIdResponse> identifyPlant(
            @Part MultipartBody.Part image
    );
}