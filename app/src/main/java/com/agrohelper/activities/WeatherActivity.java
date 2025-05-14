package com.agrohelper.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class WeatherActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    
    private WeatherApiClient weatherApiClient;
    private FusedLocationProviderClient fusedLocationClient;
    
    private ProgressBar progressBar;
    private View weatherContainer;
    private TextView temperatureTextView;
    private TextView descriptionTextView;
    private TextView humidityTextView;
    private TextView windTextView;
    private TextView recommendationTextView;
    private ImageView weatherIconImageView;

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
        
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        fetchWeatherData(location.getLatitude(), location.getLongitude());
                    } else {
                        Toast.makeText(this, R.string.location_not_available, Toast.LENGTH_SHORT).show();
                        // Use default location
                        fetchWeatherData(55.7558, 37.6173); // Moscow coordinates
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, R.string.location_error, Toast.LENGTH_SHORT).show();
                    // Use default location
                    fetchWeatherData(55.7558, 37.6173); // Moscow coordinates
                });
    }

    private void fetchWeatherData(double latitude, double longitude) {
        progressBar.setVisibility(View.VISIBLE);
        weatherContainer.setVisibility(View.GONE);
        
        weatherApiClient.getWeatherByLocation(latitude, longitude, new WeatherApiClient.WeatherCallback() {
            @Override
            public void onWeatherDataReceived(WeatherData weatherData) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    weatherContainer.setVisibility(View.VISIBLE);
                    
                    temperatureTextView.setText(getString(R.string.temperature_format, weatherData.getTemperature()));
                    descriptionTextView.setText(weatherData.getDescription());
                    humidityTextView.setText(getString(R.string.humidity_format, weatherData.getHumidity()));
                    windTextView.setText(getString(R.string.wind_format, weatherData.getWindSpeed()));
                    recommendationTextView.setText(weatherData.getRecommendation());
                    
                    // Load weather icon
                    String iconUrl = "https://openweathermap.org/img/w/" + weatherData.getIcon() + ".png";
                    Glide.with(WeatherActivity.this)
                            .load(iconUrl)
                            .into(weatherIconImageView);
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(WeatherActivity.this, R.string.weather_error, Toast.LENGTH_SHORT).show();
                });
            }
        });
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
