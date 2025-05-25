package com.agrohelper.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.agrohelper.R;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.settings);
        }

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize views
        darkModeSwitch = findViewById(R.id.dark_mode_switch);
        Button saveButton = findViewById(R.id.save_button);

        // Load current settings
        loadSettings();

        // Set up save button
        saveButton.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        // Load dark mode setting
        boolean darkMode = sharedPreferences.getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(darkMode);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save dark mode setting
        boolean isDarkMode = darkModeSwitch.isChecked();
        editor.putBoolean("dark_mode", isDarkMode);

        // Apply changes
        editor.apply();

        // Apply dark mode
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();

        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        finish();
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