package com.agrohelper.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.agrohelper.R;
import com.agrohelper.models.WeatherData;
import com.agrohelper.utils.WeatherApiClient;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.card.MaterialCardView;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    
    private WeatherApiClient weatherApiClient;
    private FusedLocationProviderClient fusedLocationClient;
    
    private ProgressBar progressBar;
    private MaterialCardView weatherContainer;
    private TextView temperatureTextView;
    private TextView descriptionTextView;
    private TextView humidityTextView;
    private TextView windTextView;
    private TextView recommendationTextView;
    private ImageView weatherIconImageView;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        
        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.weather);
        }
        
        // Initialize views
        progressBar = findViewById(R.id.progress_bar);
        weatherContainer = findViewById(R.id.weather_container);
        temperatureTextView = findViewById(R.id.temperature_text);
        descriptionTextView = findViewById(R.id.description_text);
        humidityTextView = findViewById(R.id.humidity_text);
        windTextView = findViewById(R.id.wind_text);
        recommendationTextView = findViewById(R.id.recommendation_text);
        weatherIconImageView = findViewById(R.id.weather_icon);
        errorTextView = findViewById(R.id.error_text);
        
        // Initialize weather client
        weatherApiClient = new WeatherApiClient(this);
        
        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        // Check for location permission
        if (hasLocationPermission()) {
            getLastLocation();
        } else {
            requestLocationPermission();
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                // Use default location
                fetchWeatherData(55.7558, 37.6173); // Moscow coordinates
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        Log.d(TAG, "Getting current location...");
        
        // Сначала пробуем получить текущее местоположение
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return this;
            }
        }).addOnSuccessListener(this, location -> {
            if (location != null) {
                Log.d(TAG, "Current location obtained: " + location.getLatitude() + ", " + location.getLongitude());
                fetchWeatherData(location.getLatitude(), location.getLongitude());
            } else {
                // Если не удалось получить текущее местоположение, пробуем получить последнее известное
                Log.d(TAG, "Current location is null, trying last known location");
                getLastKnownLocation();
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting current location", e);
            getLastKnownLocation();
        });
    }
    
    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        Log.d(TAG, "Last known location obtained: " + location.getLatitude() + ", " + location.getLongitude());
                        fetchWeatherData(location.getLatitude(), location.getLongitude());
                    } else {
                        Log.d(TAG, "Last known location is null, using default location");
                        Toast.makeText(this, R.string.location_not_available, Toast.LENGTH_SHORT).show();
                        // Use default location
                        fetchWeatherData(55.7558, 37.6173); // Moscow coordinates
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting last location", e);
                    Toast.makeText(this, R.string.location_error, Toast.LENGTH_SHORT).show();
                    // Use default location
                    fetchWeatherData(55.7558, 37.6173); // Moscow coordinates
                });
    }

    private void fetchWeatherData(double latitude, double longitude) {
        progressBar.setVisibility(View.VISIBLE);
        weatherContainer.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        
        Log.d(TAG, "Fetching weather data for location: " + latitude + ", " + longitude);
        
        // Проверяем, установлен ли API ключ
        if ("YOUR_OPENWEATHER_API_KEY".equals(WeatherApiClient.API_KEY) ||
            "".equals(WeatherApiClient.API_KEY)) {
            Log.d(TAG, "API key not set, using mock data");
            // Если API ключ не установлен, используем тестовые данные
            weatherApiClient.getMockWeatherData(new WeatherApiClient.WeatherCallback() {
                @Override
                public void onWeatherDataReceived(WeatherData weatherData) {
                    runOnUiThread(() -> updateWeatherUI(weatherData));
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> showError(e.getMessage()));
                }
            });
        } else {
            // Если API ключ установлен, получаем реальные данные
            weatherApiClient.getWeatherByLocation(latitude, longitude, new WeatherApiClient.WeatherCallback() {
                @Override
                public void onWeatherDataReceived(WeatherData weatherData) {
                    runOnUiThread(() -> updateWeatherUI(weatherData));
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "Weather API error", e);
                    runOnUiThread(() -> showError(e.getMessage()));
                }
            });
        }
    }
    
    private void updateWeatherUI(WeatherData weatherData) {
        progressBar.setVisibility(View.GONE);
        weatherContainer.setVisibility(View.VISIBLE);
        
        // Анимация появления карточки
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        weatherContainer.startAnimation(fadeIn);
        
        temperatureTextView.setText(getString(R.string.temperature_format, weatherData.getTemperature()));
        descriptionTextView.setText(weatherData.getDescription());
        humidityTextView.setText(getString(R.string.humidity_format, weatherData.getHumidity()));
        windTextView.setText(getString(R.string.wind_format, weatherData.getWindSpeed()));
        recommendationTextView.setText(weatherData.getRecommendation());
        
        // Load weather icon with animation
        String iconUrl = "https://openweathermap.org/img/w/" + weatherData.getIcon() + ".png";
        Glide.with(WeatherActivity.this)
                .load(iconUrl)
                .into(weatherIconImageView);
        
        // Анимация иконки
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        weatherIconImageView.startAnimation(rotate);
        
        Log.d(TAG, "Weather UI updated successfully");
    }
    
    private void showError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        weatherContainer.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        
        // Анимация появления ошибки
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        errorTextView.startAnimation(fadeIn);
        
        errorTextView.setText(getString(R.string.weather_error) + "\n" + errorMessage);
        Log.e(TAG, "Weather error: " + errorMessage);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
