package com.agrohelper.utils;

import android.content.Context;
import android.util.Log;

import com.agrohelper.models.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApiClient {
    private static final String TAG = "WeatherApiClient";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "YOUR_OPENWEATHER_API_KEY"; // Replace with your API key
    
    private final OkHttpClient client;
    private final Context context;

    public interface WeatherCallback {
        void onWeatherDataReceived(WeatherData weatherData);
        void onFailure(Exception e);
    }

    public WeatherApiClient(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public void getWeatherByLocation(double latitude, double longitude, WeatherCallback callback) {
        String url = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + 
                     "&units=metric&appid=" + API_KEY;
        
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to fetch weather data", e);
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(new IOException("Unexpected response code: " + response));
                    return;
                }

                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    
                    // Parse main weather data
                    JSONObject main = jsonObject.getJSONObject("main");
                    double temperature = main.getDouble("temp");
                    double humidity = main.getDouble("humidity");
                    
                    // Parse weather description
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);
                    String description = weather.getString("description");
                    String icon = weather.getString("icon");
                    
                    // Parse wind data
                    JSONObject wind = jsonObject.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");
                    
                    // Parse precipitation (if available)
                    double precipitation = 0;
                    if (jsonObject.has("rain")) {
                        JSONObject rain = jsonObject.getJSONObject("rain");
                        if (rain.has("1h")) {
                            precipitation = rain.getDouble("1h");
                        }
                    }
                    
                    WeatherData weatherData = new WeatherData(
                            temperature,
                            humidity,
                            description,
                            windSpeed,
                            precipitation,
                            icon
                    );
                    
                    callback.onWeatherDataReceived(weatherData);
                    
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON", e);
                    callback.onFailure(e);
                }
            }
        });
    }
}
